package com.example.onlineshop.service;

import com.example.onlineshop.entity.Purchase;
import com.example.onlineshop.entity.Review;
import com.example.onlineshop.entity.User;
import java.util.List;

public interface ReviewService {
    Review createReview(User user, Purchase purchase, String text, int rating);
    void delete(Review review);
    Double getAverageRating(Long productId);
    List<Review> getAll();

}
