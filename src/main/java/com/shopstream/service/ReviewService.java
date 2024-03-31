package com.shopstream.service;

import com.shopstream.exception.ProductException;
import com.shopstream.model.Review;
import com.shopstream.model.User;
import com.shopstream.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    public Review createReview(ReviewRequest req, User user) throws ProductException;

    public List<Review> getAllReview(Long productId);


}
