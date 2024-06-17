package com.example.feedbackservice.feedback.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.example.feedbackservice.feedback.entity.FeedbackType;

public class FeedbackTypeConverter implements DynamoDBTypeConverter<String, FeedbackType> {
    @Override
    public String convert(FeedbackType feedbackType) {
        return feedbackType.name();
    }

    @Override
    public FeedbackType unconvert(String s) {
        return FeedbackType.valueOf(s);
    }
}
