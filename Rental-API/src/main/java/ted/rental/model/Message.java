package ted.rental.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class Message {

    private Integer id;

    private Message latest_message;

    @NotNull
    @Length(max = 200)
    private String message;

    @NotNull
    @Length(max = 200)
    private String subject;

    @NotNull
    private String author;

    private String recipient;

    private boolean read;

    private boolean deleted;

    private Date created;

    public Message() {
    }

    public Message(Integer id, Message latest_message, String message, String subject, String author,
                   String recipient, boolean read, boolean deleted, Date created) {
        this.id = id;
        this.latest_message = latest_message;
        this.message = message;
        this.subject = subject;
        this.author = author;
        this.recipient = recipient;
        this.read = read;
        this.deleted = deleted;
        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Message getLatest_message() {
        return latest_message;
    }

    public void setLatest_message(Message latest_message) {
        this.latest_message = latest_message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", latest_message=" + latest_message +
                ", message='" + message + '\'' +
                ", subject='" + subject + '\'' +
                ", author='" + author + '\'' +
                ", recipient='" + recipient + '\'' +
                ", created=" + created +
                '}';
    }
}
