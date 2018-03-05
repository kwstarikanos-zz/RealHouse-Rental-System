package ted.rental.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import ted.rental.annotations.Role;
import ted.rental.database.entities.HostEntity;
import ted.rental.database.entities.RenterEntity;

import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Host extends Renter {

    //TODO: private Collection<room>
    private String about;

    private Collection<Room> rooms;

    private Boolean confirmed;

    public Host(){
        super();
    }

    /*OK -- POST constructor*/
    public Host(Profile profile, String username, String password, String email, Boolean confirmed) {
        super(profile, username, password, email);
        this.confirmed = confirmed;
    }

    /*OK -- GET constructor*/
    public Host(final HostEntity entity, final String about, Boolean confirmed) {
        super(entity, 0);
        this.about = about;
        this.confirmed = confirmed;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Role getRole() {
        return Role.host;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Collection<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Collection<Room> rooms) {
        this.rooms = rooms;
    }

    /*Προσθήκη δωματίου προς ενικοίαση.*/
    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    /*Αφαίρεση δωματίου.*/
    public void removeRoom(Room room) {
        this.rooms.remove(room);
    }

    public HostEntity toEntity() {
        Profile profile = this.getProfile();

        return new HostEntity(this.getUsername(), this.getPassword(), this.getEmail(), getRole().toString(),
                this.getCreated(), profile);
    }

}
