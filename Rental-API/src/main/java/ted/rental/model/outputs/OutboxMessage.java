package ted.rental.model.outputs;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class OutboxMessage {

    private Integer id;

    @NotNull
    @Length(max = 200)
    private String subject;

    @NotNull
    @Length(max = 200)
    private String message;

    @NotNull
    private String recipient;

    private boolean read;


    private Date created;

    public OutboxMessage() {
    }

    public OutboxMessage(Integer id, String subject, String message,
                         String recipient, boolean read, Date created) {
        this.id = id;
        this.subject = subject;
        this.message = message;
        this.recipient = recipient;
        this.read = read;
        this.created = created;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "OutboxMessage{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", recipient='" + recipient + '\'' +
                ", read=" + read +
                ", created=" + created +
                '}';
    }
}
