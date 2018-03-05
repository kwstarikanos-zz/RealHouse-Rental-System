package ted.rental.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.Length;
import ted.rental.database.entities.ProfileEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;

import static ted.rental.config.Constraint.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Profile {

    @Length(min = USERNAME_MIN, max = USERNAME_MAX)
    @Pattern(regexp = USERNAME_PATTERN)
    private String owner;

    @NotNull
    @Length(min = FIRSTNAME_MIN, max = FIRSTNAME_MAX)
    @Pattern(regexp = FIRSTNAME_PATTERN)
    private String firstname;

    @NotNull
    @Length(min = LASTNAME_MIN, max = LASTNAME_MAX)
    @Pattern(regexp = LASTNAME_PATTERN)
    private String lastname;

    @NotNull
    private Date birthday;

    @NotNull
    @Pattern(regexp = PHONE_PATTERN)
    @Length(min = PHONE_MIN, max = PHONE_MAX)
    private String phone;

    @Valid
    private Location location;

    private Picture picture;

    private Collection<Comment> comments = new HashSet<>();

    public Profile() {

    }

    /*POST - GET Constructor*/
    public Profile(String owner, String firstname, String lastname, Date birthday, String phone,
                   Location location, Picture picture, Collection<Comment> comments) {
        this.owner = owner;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.phone = phone;
        this.location = location;
        this.picture = picture;
        this.comments = comments;
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

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "ProfileEntity{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthday=" + birthday +
                ", phone='" + phone + '\'' +
                ",\n\n location=" + location +
                ",\n\n picture=" + picture +
                '}';
    }

    public ProfileEntity toEntity() {
        return new ProfileEntity(this.owner, this.firstname, this.lastname, this.birthday,
                this.phone, this.location == null ? null : location.getId(),
                this.location == null ? null : this.location.toEntity(),
                this.picture == null? null : this.picture.toEntity());
    }
}
