package ted.rental.database.entities;

import ted.rental.model.Review;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NamedQueries({
        @NamedQuery(name = "reviews.findById", query = "SELECT r from ReviewEntity r where r.id = :id"),
        @NamedQuery(name = "reviews.findByIdOrderByDate", query = "SELECT r from ReviewEntity  r where r.roomEntity.id = :id order by r.created DESC "),
        @NamedQuery(name = "reviews.deleteById", query = "DELETE from ReviewEntity r where r.id = :id")
})
public class ReviewEntity {

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

    @Basic
    @Column(name = "rating", nullable = false)
    private double rating;

      /*                                                                         */
     /*                 Σχέση Ένα-Προς-Ένα ((Review))-((Renter)                 */
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
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private RoomEntity roomEntity;

    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }

    public ReviewEntity() {
    }

    public ReviewEntity(String message, Timestamp created, double rating, RenterEntity author, RoomEntity roomEntity) {
        this.message = message;
        this.created = created;
        this.rating = rating;
        this.author = author;
        this.roomEntity = roomEntity;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "ReviewEntity{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", created=" + created +
                ", rating=" + rating +
                ", author=" + author +
                ", roomEntity=" + roomEntity +
                '}';
    }

    public Review toModel(){
        return new Review(this.id, this.author.getUsername(), this.message, this.rating, this.created);
    }
}
