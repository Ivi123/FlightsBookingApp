package com.example.feedbackservice.review.service;

import com.example.feedbackservice.review.entity.Review;
import com.example.feedbackservice.review.entity.ReviewScore;
import com.example.feedbackservice.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public Review getReviewById(String id) {
        return reviewRepository.getReviewById(id);
    }

    public String delete(String id) {
        return reviewRepository.delete(id);
    }

    public String update(String id, Review review) {
        return reviewRepository.update(id, review);
    }

    public List<Review> getAllByOperatorId(String id) {
        return reviewRepository.getAllByOperatorId(id);
    }

    public String getScoreByOperatorId(String id) {
        List<Review> reviews = reviewRepository.getAllByOperatorId(id);
        List<Integer> scores = reviews
                .stream()
                .map(review -> convertScore(review.getScore()))
                .collect(Collectors.toList());

        double score = scores.stream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
        return "Review score for " + id + " is " + score;
    }

    public Integer convertScore(ReviewScore reviewScore) {
        return switch (reviewScore) {
            case ONE_STAR -> 1;
            case TWO_STARS -> 2;
            case THREE_STARS -> 3;
            case FOUR_STARS -> 4;
            case FIVE_STARS -> 5;
        };
    }
}
