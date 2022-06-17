package tec.bd.social.service;

import tec.bd.social.Rating;
import tec.bd.social.repository.RatingsRepository;

public class RatingsServiceImpl implements RatingsService {

    private RatingsRepository ratingsRepository;

    public RatingsServiceImpl(RatingsRepository ratingsRepository){
        this.ratingsRepository = ratingsRepository;
    }


    @Override
    public Rating getRating(int ratingId) {
        return this.ratingsRepository.findById(ratingId);
    }

    @Override
    public float getRatingAverage(String todoId) {
        return 0;
    }
}
