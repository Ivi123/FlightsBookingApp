package com.example.feedbackservice.review.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.feedbackservice.review.entity.Review;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewRepository {
    private final DynamoDBMapper dynamoDBMapper;

    public ReviewRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public Review save(Review review) {
        dynamoDBMapper.save(review);
        return review;
    }

    public Review getReviewById(String id) {
        return dynamoDBMapper.load(Review.class, id);
    }

    public String delete(String id) {
        Review review = dynamoDBMapper.load(Review.class, id);
        dynamoDBMapper.delete(review);
        return "review deleted!";
    }

    public String update(String id, Review review) {
        dynamoDBMapper.save(review,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("employeeId",
                                new ExpectedAttributeValue(
                                        new AttributeValue().withS(id)
                                )));
        return id;
    }

    public List<Review> getAllByOperatorId(String operatorId) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterConditionEntry("operatorId", new Condition()
                        .withComparisonOperator(ComparisonOperator.EQ.toString())
                        .withAttributeValueList(new AttributeValue().withS(operatorId)));
        return dynamoDBMapper.scan(Review.class, scanExpression);
    }
}
