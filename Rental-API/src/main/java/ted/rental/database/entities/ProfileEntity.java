package ted.rental.database.entities;

import ted.rental.model.Comment;
import ted.rental.model.Profile;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static ted.rental.config.Constraint.*;

@Entity
//@Table(name ="profiles", schema = "InheritanceExample")
@NamedQueries({
        @NamedQuery(name = "profiles.findAll", query = "SELECT p FROM ProfileEntity p"),
        @NamedQuery(name = "profiles.findByOwner", query = "SELECT p FROM ProfileEntity p WHERE p.owner = :owner"),
        @NamedQuery(name = "profiles.deleteByOwner", query = "DELETE FROM ProfileEntity p WHERE p.owner = :owner"),
        @NamedQuery(name = "profiles.findProfileByPictureId", query = "SELECT p FROM ProfileEntity p where p.pictureEntity.picture_id =:id")
})

public class ProfileEntity implements Serializable {

    @Id
    @Column(name = "owner", unique = true, nullable = false, length = USERNAME_MAX)
    private String owner;

    @Basic
    @Column(name = "firstname", nullable = false, length = FIRSTNAME_MAX)
    private String firstname;

    @Basic
    @Column(name = "lastname", nullable = false, length = LASTNAME_MAX)
    private String lastname;

    @Basic
    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @Basic
    @Column(name = "phone", unique = true, nullable = false, length = PHONE_MAX)
    private String phone;


      /*                                                                           */
     /*                 Σχέση Ένα-Προς-Ένα ((Profile))-((Location))               */
    /*                                                                           */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private LocationEntity locationEntity;

      /*                                                                           */
     /*                 Σχέση Ένα-Προς-Ένα ((Profile))-((Renter))                 */
    /*                                                                           */
    @OneToOne
    @JoinColumn(name = "owner", referencedColumnName = "username", insertable = false, updatable = false)
    private RenterEntity renterEntity;

      /*                                                                           */
     /*                 Σχέση Ένα-Προς-Ένα ((Profile))-((Picture))                */
    /*                                                                           */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "picture_id", referencedColumnName = "id")
    private PictureEntity pictureEntity;

      /*                                                                           */
     /*                 Σχέση Ένα-Προς-Ένα ((Profile))-((Comment))                */
    /*                                                                           */
    @OneToMany(mappedBy = "profileEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<CommentEntity> commentEntities = new HashSet<>();

    public Collection<CommentEntity> getCommentEntities() {
        return commentEntities;
    }

    public void setCommentEntities(Collection<CommentEntity> commentEntities) {
        this.commentEntities = commentEntities;
    }

    public ProfileEntity() {
        super();
    }

    public ProfileEntity(String owner, String firstname, String lastname, Date birthday,
                         String phone, Integer locationId, LocationEntity locationEntity,
                         PictureEntity pictureEntity) {
        super();
        this.owner = owner;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.phone = phone;
        this.locationEntity = locationEntity;
        this.pictureEntity = pictureEntity;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocationEntity getLocationEntity() {
        return locationEntity;
    }

    public void setLocationEntity(LocationEntity locationEntity) {
        this.locationEntity = locationEntity;
    }

    public RenterEntity getRenterEntity() {
        return renterEntity;
    }

    public void setRenterEntity(RenterEntity renterEntity) {
        this.renterEntity = renterEntity;
    }

    public PictureEntity getPictureEntity() {
        return pictureEntity;
    }

    public void setPictureEntity(PictureEntity pictureEntity) {
        this.pictureEntity = pictureEntity;
    }


    @Override
    public String toString() {
        return "ProfileEntity{" +
                "owner='" + owner + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthday=" + birthday +
                ", phone='" + phone + '\'' +
                ", locationEntity=" + locationEntity +
                '}';
    }

    public Profile toModel() {
        return new Profile(this.owner, this.firstname, this.lastname, this.birthday, this.phone,
                this.locationEntity == null ? null : this.locationEntity.toModel(),
                this.pictureEntity == null ? null : this.pictureEntity.toModel(),
                this.commentEntities ==null? null : this.toCommentsModel());
    }

    private Collection<Comment> toCommentsModel() {
        Collection<Comment> comments = new ArrayList<>();
        for (CommentEntity commentEntity : this.commentEntities) {
            comments.add(commentEntity.toModel());
        }
        return comments;

    }

}