package com.example.feedbackservice.feedback.controller;

import com.example.feedbackservice.feedback.entity.Feedback;
import com.example.feedbackservice.feedback.service.FeedbackService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/add")
    public Feedback saveFeedback(@RequestBody Feedback feedback) {
        return feedbackService.save(feedback);
    }

    @GetMapping("/{id}")
    public Feedback getFeedbackById(@PathVariable String id) {
        return feedbackService.getFeedbackById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteFeedbackById(@PathVariable String id) {
        return feedbackService.delete(id);
    }

    @PutMapping("/{id}")
    public String updateFeedback(@PathVariable String id, @RequestBody Feedback feedback) {
        return feedbackService.update(id, feedback);
    }

    @GetMapping("/all/flight/{id}")
    public List<Feedback> getAllFeedbacksForFlight(@PathVariable String id) {
        return feedbackService.getAllFeedbacksForFlight(id);
    }

    @GetMapping("/all/booking/{id}")
    public List<Feedback> getAllFeedbacksForBooking(@PathVariable String id) {
        return feedbackService.getAllFeedbacksForBooking(id);
    }
}
