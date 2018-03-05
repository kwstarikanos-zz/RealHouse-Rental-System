package ted.rental.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ted.rental.annotations.Role;
import ted.rental.model.Picture;
import ted.rental.model.Profile;
import ted.rental.model.Renter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@DiscriminatorValue("renter")
@PrimaryKeyJoinColumn(referencedColumnName="username")
@NamedQueries({
        @NamedQuery(name = "renter.findByPhone", query = "SELECT r FROM RenterEntity r where r.profileEntity.phone = :phone"),
        @NamedQuery(name = "renter.findByUsername", query = "SELECT r FROM RenterEntity r where r.username = :username")
})


public class RenterEntity extends UserEntity {


    public RenterEntity(){
        super();
    }

    public RenterEntity(String username, String password, String email, String role, Timestamp created, Profile profile) {
        super(username, password, email, role, created);
        this.profileEntity = profile == null ? null : profile.toEntity();
    }


      /*                                                                           */
     /*                 Σχέση Ένα-Προς-Ένα ((Renter))-((Profile))                 */
    /*                                                                           */
    @OneToOne(mappedBy = "renterEntity", cascade = CascadeType.ALL)
    private ProfileEntity profileEntity;

    public ProfileEntity getProfileEntity() {
        return profileEntity;
    }

    public void setProfileEntity(ProfileEntity profileEntity) {
        this.profileEntity = profileEntity;
    }


      /*                                                                           */
     /*                 Σχέση Ένα-Προς-Ένα ((Renter))-((Reservation))             */
    /*                                                                           */
    @OneToOne(mappedBy = "renterEntity", cascade = CascadeType.ALL)
    private ReservationEntity reservationEntity;

    public ReservationEntity getReservationEntity() {
        return reservationEntity;
    }



    public void setReservationEntity(ReservationEntity reservationEntity) {
        this.reservationEntity = reservationEntity;
    }

    public String getRole() {
        return Role.renter.toString();
    }

    public Renter toModel(Integer depth){
        return new Renter(this, depth);
    }

    @Override
    public String toString() {
        return "RenterEntity{" +
                "profileEntity=" + profileEntity +
                "} " + super.toString();
    }
}
