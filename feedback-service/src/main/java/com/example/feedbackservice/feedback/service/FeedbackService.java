package com.example.feedbackservice.feedback.service;

import com.example.feedbackservice.feedback.entity.Feedback;
import com.example.feedbackservice.feedback.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public Feedback getFeedbackById(String id) {
        return feedbackRepository.getFeedbackById(id);
    }

    public String delete(String id) {
        return feedbackRepository.delete(id);
    }

    public String update(String id, Feedback feedback) {
        return feedbackRepository.update(id, feedback);
    }

    public List<Feedback> getAllFeedbacksForFlight(String id) {
        return feedbackRepository.getAllFeedbacksForFlight(id);
    }

    public List<Feedback> getAllFeedbacksForBooking(String id) {
        return feedbackRepository.getAllFeedbacksForBooking(id);
    }
}

