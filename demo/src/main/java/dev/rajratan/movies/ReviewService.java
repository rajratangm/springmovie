package dev.rajratan.movies;

import dev.rajratan.movies.Movie;
import dev.rajratan.movies.Review;
import dev.rajratan.movies.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
//
//@Service
//public class ReviewService {
//    @Autowired
//    private ReviewRepository repository;
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    public Review createReview(String reviewBody, String imdbId) {
//        Review review = repository.push(new Review(reviewBody, LocalDateTime.now(), LocalDateTime.now()));
//
//        mongoTemplate.update(Movie.class)
//                .matching(Criteria.where("imdbId").is(imdbId))
//                .apply(new Update().push("reviewIds").value(review.getId()))
//                .first();
//        return review;
//    }
//}


@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Review createReview(String reviewBody, String imdbId) {
        // Create a new Review object
        Review review = new Review();
        review.setReviewBody(reviewBody);

        // Save the review in the repository
        review = reviewRepository.save(review);

        // Update the movie with the review ID
        Update update = new Update().push("reviewIds", review.getId());
        mongoTemplate.updateFirst(Query.query(Criteria.where("imdbId").is(imdbId)), update, Movie.class);

        return review;
    }
}
