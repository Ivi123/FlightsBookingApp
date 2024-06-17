package com.example.feedbackservice.feedback.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.feedbackservice.feedback.entity.Feedback;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class FeedbackRepository {
    private final DynamoDBMapper dynamoDBMapper;

    public FeedbackRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public Feedback save(Feedback feedback) {
        dynamoDBMapper.save(feedback);
        return feedback;
    }

    public Feedback getFeedbackById(String id) {
        return dynamoDBMapper.load(Feedback.class, id);
    }

    public String delete(String id) {
        Feedback feedback = dynamoDBMapper.load(Feedback.class, id);
        dynamoDBMapper.delete(feedback);
        return "Feedback deleted!";
    }

    public String update(String id, Feedback feedback) {
        dynamoDBMapper.save(feedback,
                new DynamoDBSaveExpression()
                .withExpectedEntry("employeeId",
                        new ExpectedAttributeValue(
                                new AttributeValue().withS(id)
                        )));
        return id;
    }

    public List<Feedback> getAllFeedbacksForFlight(String flightId) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterConditionEntry("flightId", new Condition()
                        .withComparisonOperator(ComparisonOperator.EQ.toString())
                        .withAttributeValueList(new AttributeValue().withS(flightId)));
        return dynamoDBMapper.scan(Feedback.class, scanExpression);
    }

    public List<Feedback> getAllFeedbacksForBooking(String bookingId) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterConditionEntry("bookingId", new Condition()
                        .withComparisonOperator(ComparisonOperator.EQ.toString())
                        .withAttributeValueList(new AttributeValue().withS(bookingId)));
        return dynamoDBMapper.scan(Feedback.class, scanExpression);
    }
}
