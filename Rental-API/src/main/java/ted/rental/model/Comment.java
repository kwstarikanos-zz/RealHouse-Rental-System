package ted.rental.model;


import java.sql.Timestamp;
import java.util.Date;

public class Comment {

    private Integer id;

    private String message;

    private Date created;

    private String author;

    public Comment() {
    }

    public Comment(Integer id, String message, String author, Timestamp created) {
        this.id = id;
        this.message = message;
        this.author = author;
        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "message='" + message + '\'' +
                ", created=" + created +
                ", author='" + author + '\'' +
                '}';
    }
}
