package ted.rental.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.eclipse.jetty.util.annotation.Name;
import ted.rental.annotations.Role;
import ted.rental.database.entities.ProfileEntity;
import ted.rental.database.entities.RenterEntity;

import javax.persistence.NamedQuery;
import javax.validation.Valid;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Renter extends User {

    @JsonIgnore
    @Valid
    private Profile profile;

    public Renter(){
        super();
    }

    /*OK -- POST constructor*/
    public Renter(Profile profile, String username, String password, String email) {
        super(username, password, email, Role.renter);
        this.profile = profile;
    }

    /*OK -- GET constructor*/
    public Renter(final RenterEntity entity, Integer depth) {
        super(entity.getUsername(), entity.getPassword(), entity.getEmail(), Role.renter, entity.getCreated());
        ProfileEntity profileEntity = entity.getProfileEntity();
        this.profile = profileEntity == null || depth == 1 ? null : profileEntity.toModel();
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Role getRole() {
        return Role.renter;
    }

    public Boolean getConfirmed() {
        return true;
    }

    /*Κράτηση*/
    public void reservation(Room room) {
        /*TODO: <----------------------------------------------------------------------------------------------------*/
    }

    /*Ακύρωση κρατησης*/
    public void cancelReservation(Room room) {
        /*TODO: <----------------------------------------------------------------------------------------------------*/
    }

    /*Πληρωμή*/
    public void payment(Room room) {
        /*TODO: <----------------------------------------------------------------------------------------------------*/
    }

    /*Ακύρωση πληρωμής*/
    public void cancelPayment(Room room) {
        /*TODO: <----------------------------------------------------------------------------------------------------*/
    }

    /*Αποστολή προσωπικού μηνύματος*/
    public void sentMessage() {
        /*TODO: <----------------------------------------------------------------------------------------------------*/
    }


    /*Λήψη νέων μηνυμάτων*/
    public void receiveMessages() {
        /*TODO: <----------------------------------------------------------------------------------------------------*/
    }

    /*Σχολιασμός προφίλ*/
    public void commentProfile(Profile profile, String message, Integer vote) {
        //profile.setComment(message, vote);
        /*TODO: <----------------------------------------------------------------------------------------------------*/
    }

    /*Σλολιασμός κατοικίας*/
    public void commentRoom(Room room, String message, Integer vote) {
        //room.setComment(message, vote);
        /*TODO: <----------------------------------------------------------------------------------------------------*/
    }

    public RenterEntity toEntity() {
        return new RenterEntity(this.getUsername(), this.getPassword(), this.getEmail(), getRole().toString(),
                this.getCreated(), this.profile);
    }

    @Override
    public String toString() {
        return "Renter{" +
                "profile=" + profile +
                "} " + super.toString();
    }
}
