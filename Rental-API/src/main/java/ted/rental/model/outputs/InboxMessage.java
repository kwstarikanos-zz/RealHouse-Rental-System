package ted.rental.model.outputs;

import org.hibernate.validator.constraints.Length;
import ted.rental.model.Message;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class InboxMessage {

    private Integer id;

    @NotNull
    @Length(max = 200)
    private String subject;

    @NotNull
    @Length(max = 200)
    private String message;

    @NotNull
    private String author;

    private boolean read;

    private Date created;

    public InboxMessage() {
    }

    public InboxMessage(Integer id, String subject, String message, String author, boolean read, Date created) {
        this.id = id;
        this.subject = subject;
        this.message = message;
        this.author = author;
        this.read = read;
        this.created = created;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "InboxMessage{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", author='" + author + '\'' +
                ", read=" + read +
                ", created=" + created +
                '}';
    }
}
