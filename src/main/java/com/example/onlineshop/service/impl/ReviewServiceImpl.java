package com.example.onlineshop.service.impl;

import com.example.onlineshop.entity.Product;
import com.example.onlineshop.entity.Purchase;
import com.example.onlineshop.entity.Review;
import com.example.onlineshop.entity.User;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.repository.ReviewRepository;
import com.example.onlineshop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    // Создание отзыва с оценкой
    public Review createReview(User user, Purchase purchase, String text, int rating) {
        if (!user.getPurchases().contains(purchase)) {
            throw new IllegalArgumentException("User hasn't made this purchase");
        }
        Product product = purchase.getProduct();
        Review review = new Review();
        review.setContent(text);
        review.setRating(rating);
        product.getReviews().add(review);
        return review;
    }

    // Список всех отзывов
    @Override
    public List<Review> getAll() {return reviewRepository.findAll();}

    // Получить среднюю оценку отвара
    @Override
    public Double getAverageRating(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        List<Review> ratings = reviewRepository.findAByProduct(product);
        if (ratings.isEmpty()) {
            return null;
        }
        double sum = ratings.stream().mapToInt(Review::getRating).sum();
        return sum / ratings.size();
    }
    // Удалить отзыв
    @Override
    public void delete(Review review){
        reviewRepository.delete(review);
    }

}
