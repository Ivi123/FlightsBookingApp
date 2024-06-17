package com.example.feedbackservice.review.controller;

import com.example.feedbackservice.review.entity.Review;
import com.example.feedbackservice.review.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    public Review saveReview(@RequestBody Review review) {
        return reviewService.save(review);
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable String id) {
        return reviewService.getReviewById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteReviewById(@PathVariable String id) {
        return reviewService.delete(id);
    }

    @PutMapping("/{id}")
    public String updateReview(@PathVariable String id, @RequestBody Review review) {
        return reviewService.update(id, review);
    }

    @GetMapping("/all/operator/{id}")
    public List<Review> getAllByOperatorId(@PathVariable String id) {
        return reviewService.getAllByOperatorId(id);
    }

    @GetMapping("/score/{id}")
    public String getScoreByOperatorId(@PathVariable String id) {
        return reviewService.getScoreByOperatorId(id);
    }
}
