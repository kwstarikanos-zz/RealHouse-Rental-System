package ted.rental.database.entities;

import ted.rental.database.entities.ProfileEntity;
import ted.rental.database.entities.RenterEntity;
import ted.rental.model.Comment;

import javax.persistence.*;
import java.sql.Timestamp;



@Entity
@NamedQueries({
        @NamedQuery(name = "comments.findById", query = "SELECT c from CommentEntity c where c.id= :id"),
        @NamedQuery(name ="comments.findByIdOrderByDate", query = "SELECT c from CommentEntity  c where c.profileEntity.owner = :owner order by c.created DESC "),
        @NamedQuery(name = "comments.deleteById", query = "DELETE from CommentEntity  c where c.id =:id")
})
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Basic
    @Column(name = "message", nullable = false, length = 200)
    private String message;

    @Basic
    @Column(name = "created", nullable = false)
    private Timestamp created;


      /*                                                                         */
     /*                 Σχέση Ένα-Προς-Ένα ((Comment))-((Renter)                */
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

      /*                                                                         */
     /*                 Σχέση Ένα-Προς-Ένα ((Comment))-((Profile)               */
    /*                                                                         */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient", referencedColumnName = "owner")
    private ProfileEntity profileEntity;

    public ProfileEntity getProfileEntity() {
        return profileEntity;
    }

    public void setProfileEntity(ProfileEntity profileEntity) {
        this.profileEntity = profileEntity;
    }


    public CommentEntity() {
    }


    public CommentEntity(String message, Timestamp created, RenterEntity author, ProfileEntity profileEntity) {
        this.message = message;
        this.created = created;
        this.author = author;
        this.profileEntity = profileEntity;
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

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "CommentEntity{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", created=" + created +
                ", author=" + author +
                ", profileEntity=" + profileEntity +
                '}';
    }


    public Comment toModel(){
        return new Comment(this.id, this.message, this.author.getUsername(), this.created);
    }
}
