package ted.rental.services;

import com.sun.org.apache.regexp.internal.RE;
import ted.rental.database.DataAccessObject;
import ted.rental.database.entities.CommentEntity;
import ted.rental.database.entities.RenterEntity;
import ted.rental.database.entities.ReviewEntity;
import ted.rental.database.entities.RoomEntity;
import ted.rental.exceptions.ErrorException;
import ted.rental.model.Comment;
import ted.rental.model.Review;

import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ReviewService {

    public ReviewService() {

    }

    public List<Review> getReviews(Integer room_id) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("id", room_id);
        dao.setStart(0);
        dao.setLimit(10);
        List<ReviewEntity> reviewEntities = dao.getTuples("reviews.findByIdOrderByDate");
        List<Review> reviews = new ArrayList<>();
        for(ReviewEntity reviewEntity : reviewEntities){
            reviews.add(reviewEntity.toModel());
        }
        return reviews;
    }

    public ReviewEntity postReview(Review review, Integer room_id) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("id", room_id);
        List<RoomEntity> roomEntities = dao.getTuples("rooms.findById");
        dao.setParam("username", review.getAuthor());
        List<RenterEntity> renterEntities = dao.getTuples("renter.findByUsername");
        ReviewEntity reviewEntity = new ReviewEntity(review.getMessage(), new Timestamp(System.currentTimeMillis()),
                review.getRating(), renterEntities.get(0), roomEntities.get(0));
        return dao.insertTuple(reviewEntity);
    }


    public void editReview(Review review, Integer review_id) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("id", review_id);
        List<ReviewEntity> reviewEntities = dao.getTuples("reviews.findById");
        reviewEntities.get(0).setMessage(review.getMessage());
        reviewEntities.get(0).setRating(review.getRating());
        dao.updateTuple(reviewEntities.get(0));
    }

    public void deleteReview(Integer review_id) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("id", review_id);
        dao.removeTuple("reviews.deleteById");
    }

    public String retrieveReviewOwner(Integer review_id) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("id", review_id);
        List<ReviewEntity> reviewEntities = dao.getTuples("reviews.findById");
        if(reviewEntities.isEmpty()){
            throw new ErrorException(Response.Status.NOT_FOUND, "The review you are trying to access does not exist!");
        }
        return reviewEntities.get(0).getAuthor().getUsername();
    }


}



