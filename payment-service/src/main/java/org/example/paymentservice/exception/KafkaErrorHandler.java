package org.example.paymentservice.exception;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.RecordDeserializationException;
import org.example.paymentservice.consumer.dlt.DltConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class KafkaErrorHandler implements CommonErrorHandler {
    private final DltConsumerService dltConsumerService;
    private static final Logger log = LoggerFactory.getLogger(KafkaErrorHandler.class);

    public KafkaErrorHandler(DltConsumerService dltConsumerService) {
        this.dltConsumerService = dltConsumerService;
    }

    // Handle any other type of exception that occurs during message consumption
    @Override
    public void handleOtherException(Exception exception, Consumer<?, ?> consumer, MessageListenerContainer container, boolean batchListener) {
        handle(exception, consumer);
    }

    private void handle(Exception exception, Consumer<?, ?> consumer) {
        log.error("Exception thrown", exception);
        if (exception instanceof RecordDeserializationException ex) {
            // If the exception is due to deserialization error, seek to the next offset and commit the offset
            consumer.seek(ex.topicPartition(), ex.offset() + 1L);
            consumer.commitSync();
        } else {
            log.error("Exception not handled", exception);
        }
    }

    // Handle exception thrown during processing of a single record
    @Override
    public boolean handleOne(Exception exception, ConsumerRecord<?, ?> consumerRecord, Consumer<?, ?> consumer, MessageListenerContainer container) {
        handle(exception, consumer, consumerRecord);
        return true;
    }

    private void handle(Exception exception, Consumer<?, ?> consumer, ConsumerRecord<?, ?> consumerRecord) {
        log.error("Exception thrown", exception);
        // Forward the record to Dead Letter Topic (DLT) for further processing
        dltConsumerService.sendToDLT(consumerRecord);
        // Move to the next offset for the partition
        consumer.seek(new TopicPartition(consumerRecord.topic(), consumerRecord.partition()), consumerRecord.offset() + 1L);
        log.info("Record moved to DLT");
    }
}
