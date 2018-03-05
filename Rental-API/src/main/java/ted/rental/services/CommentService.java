package ted.rental.services;


import ted.rental.database.DataAccessObject;
import ted.rental.database.entities.CommentEntity;
import ted.rental.database.entities.ProfileEntity;
import ted.rental.database.entities.RenterEntity;
import ted.rental.exceptions.ErrorException;
import ted.rental.model.Comment;

import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CommentService {

    public CommentService() {

    }

    public List<Comment> getComments(String username) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("owner", username);
        dao.setStart(0);
        dao.setLimit(10);
        List<CommentEntity> commentEntities = dao.getTuples("comments.findByIdOrderByDate");
        List<Comment> comments = new ArrayList<>();
        for(CommentEntity commentEntity : commentEntities){
            comments.add(commentEntity.toModel());
        }
        return comments;
    }

    public CommentEntity postComment(Comment comment, String username) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("owner", username);
        List<ProfileEntity> profileEntities = dao.getTuples("profiles.findByOwner");
        dao.setParam("username", comment.getAuthor());
        List<RenterEntity> renterEntities = dao.getTuples("renter.findByUsername");
        CommentEntity commentEntity = new CommentEntity(comment.getMessage(), new Timestamp(System.currentTimeMillis()),
                renterEntities.get(0), profileEntities.get(0));
        CommentEntity newComment = dao.insertTuple(commentEntity);
        return newComment;
    }


    public void editComment(Comment comment, String username, Integer comment_id) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("id", comment_id);
        List<CommentEntity> commentEntities = dao.getTuples("comments.findById");
        commentEntities.get(0).setMessage(comment.getMessage());
        dao.updateTuple(commentEntities.get(0));
    }

    public void deleteComment(Integer comment_id) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("id", comment_id);
        dao.removeTuple("comments.deleteById");
    }


    public String retrieveCommentOwner(Integer comment_id) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("id", comment_id);
        List<CommentEntity> commentEntities = dao.getTuples("comments.findById");
        if(commentEntities.isEmpty()){
            throw new ErrorException(Response.Status.CONFLICT, "The comment with id " + comment_id + " does not exist!");
        }
        return commentEntities.get(0).getAuthor().getUsername();
    }
}

