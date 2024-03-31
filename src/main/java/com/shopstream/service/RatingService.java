package com.shopstream.service;

import com.shopstream.exception.ProductException;
import com.shopstream.model.Rating;
import com.shopstream.model.User;
import com.shopstream.request.RatingRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RatingService {

    public Rating createRating(RatingRequest req, User user) throws ProductException;

    public List<Rating> getProductsRating(Long productId);

}

