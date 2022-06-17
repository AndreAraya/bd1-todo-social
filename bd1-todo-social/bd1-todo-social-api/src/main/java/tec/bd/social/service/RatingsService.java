package tec.bd.social.service;

import tec.bd.social.Rating;

public interface RatingsService {

    Rating getRating(int ratingId);

    float getRatingAverage(String todoId);
}
