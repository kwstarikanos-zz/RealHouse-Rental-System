package ted.rental.database.entities;


import ted.rental.model.Message;
import ted.rental.model.outputs.InboxMessage;
import ted.rental.model.outputs.OutboxMessage;

import javax.persistence.*;
import javax.ws.rs.core.Context;
import java.sql.Timestamp;

@Entity
@NamedQueries({
        @NamedQuery(name = "messages.findLatestMessage", query = "SELECT m from MessageEntity m where m.author.username = :author and m.recipient.username = :recipient order by m.created DESC"),
        @NamedQuery(name = "messages.findInbox", query = "SELECT m from MessageEntity m where m.recipient.username =:recipient and m.deleted = false order by m.created DESC"),
        @NamedQuery(name = "messages.findOutbox", query = "SELECT m from MessageEntity m where m.author.username =:author and m.deleted = false order by m.created DESC"),
        @NamedQuery(name = "message.findById", query = "SELECT m from MessageEntity m where m.id = :id"),
        @NamedQuery(name = "messages.findDeletedMessages", query = "SELECT m from MessageEntity m where (m.author.username =:username or m.recipient.username =:username) and m.deleted = true order by m.created DESC")
})
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Basic
    @Column(name = "message", nullable = false, length = 200)
    private String message;

    @Basic
    @Column(name = "subject", nullable = false, length = 200)
    private String subject;

    @Basic
    @Column(name = "read_message", nullable = false)
    private Boolean read;

    @Basic
    @Column(name = "deleted_message", nullable = false)
    private Boolean deleted;

    @Basic
    @Column(name = "created", nullable = false)
    private Timestamp created;


      /*                                                                         */
     /*                 Σχέση Ένα-Προς-Ένα ((Message))-((Author)                */
    /*                                                                         */
    @OneToOne
    @JoinColumn(name = "author", referencedColumnName = "username")
    private RenterEntity author;

    public RenterEntity getAuthor() {
        return author;
    }

    public void setAuthor(RenterEntity author) {
        this.author = author;
    }

      /*                                                                        */
     /*                 Σχέση Ένα-Προς-Ένα ((Message))-((Recipient)            */
    /*                                                                        */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient", referencedColumnName = "username")
    private RenterEntity recipient;

    public RenterEntity getRecipient() {
        return recipient;
    }

    public void setRecipient(RenterEntity recipient) {
        this.recipient = recipient;
    }

      /*                                                                        */
     /*                 Σχέση Ένα-Προς-Ένα ((Message))-((LastMessage)          */
    /*                                                                        */
    @OneToOne
    @JoinColumn(name = "latestMessage", referencedColumnName = "id")
    private MessageEntity latestMessage;

    public MessageEntity getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(MessageEntity latestMessage) {
        this.latestMessage = latestMessage;
    }


    public MessageEntity() {
    }

    public MessageEntity(String message, String subject, Boolean read, Boolean deleted, Timestamp created,
                         RenterEntity author, RenterEntity recipient, MessageEntity latestMessage) {
        this.message = message;
        this.subject = subject;
        this.read = read;
        this.deleted = deleted;
        this.created = created;
        this.author = author;
        this.recipient = recipient;
        this.latestMessage = latestMessage;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", subject='" + subject + '\'' +
                ", created=" + created +
                ", author=" + author +
                ", recipient=" + recipient +
                ", latestMessage=" + latestMessage +
                '}';
    }

    public Message toModel() {
        return new Message(this.id, this.latestMessage == null ? null : this.latestMessage.toModel(), this.message,
                this.subject, this.getAuthor().getUsername(), this.getRecipient().getUsername(), this.read, this.deleted, this.created);
    }

    public InboxMessage toInboxMessage(){
        return new InboxMessage(this.id, this.message, this.subject, this.getAuthor().getUsername(), this.read, this.created);
    }

    public OutboxMessage toOutboxMessage(){
        return new OutboxMessage(this.id, this.message, this.subject, this.getRecipient().getUsername(), this.read, this.created);
    }
}
