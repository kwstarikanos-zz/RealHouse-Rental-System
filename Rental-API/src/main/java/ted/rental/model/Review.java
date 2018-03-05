package ted.rental.model;

import java.util.Date;

public class Review {

    private Integer id;

    private String author;

    private String message;

    private double rating;

    private Date created;

    public Review() {
    }

    public Review(Integer id, String author,
                  String message, double rating, Date created) {
        this.id = id;
        this.author = author;
        this.message = message;
        this.rating = rating;
        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", message='" + message + '\'' +
                ", rating=" + rating +
                ", created=" + created +
                '}';
    }
}
