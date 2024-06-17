package com.example.feedbackservice.review.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.example.feedbackservice.review.entity.ReviewScore;

public class ReviewScoreConverter implements DynamoDBTypeConverter<String, ReviewScore> {
    @Override
    public String convert(ReviewScore reviewScore) {
        return reviewScore.name();
    }

    @Override
    public ReviewScore unconvert(String s) {
        return ReviewScore.valueOf(s);
    }
}
