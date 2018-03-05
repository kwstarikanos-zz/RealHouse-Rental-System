package ted.rental.database.entities;

import ted.rental.annotations.Role;
import ted.rental.model.Host;
import ted.rental.model.Profile;

import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Entity
@DiscriminatorValue("host")
@PrimaryKeyJoinColumn(referencedColumnName="username")
@NamedQuery(name = "find.hostByUsername", query = "SELECT h from HostEntity h where h.username =:username")
public class HostEntity extends RenterEntity implements Serializable{


    @Basic
    @Column(name = "about", length = 200)
    private String about;

    @Basic
    @Column(name = "confirmed", nullable = false)
    private boolean confirmed;

    public HostEntity() {
        super();
    }

    public HostEntity(String username, String password, String email, String role, Timestamp created, Profile profile) {
        super(username, password, email, role, created, profile);
    }

    public HostEntity(String username, String password, String email, String role, Timestamp created, Profile profile,  boolean confirmed) {
        super(username, password, email, role, created, profile);
        this.confirmed = confirmed;
    }


      /*                                                                             */
     /*                 Σχέση Ένα-Προς-Πολλά ((Host))-((Room))                      */
    /*                                                                             */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hostEntity")
    private Collection<RoomEntity> roomEntities = new HashSet<>();

    public Collection<RoomEntity> getRoomEntities() {
        return roomEntities;
    }

    public void setRoomEntities(Collection<RoomEntity> roomEntities) {
        this.roomEntities = roomEntities;
    }

    public String getRole() {
        return Role.host.toString();
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Host toModel(Integer depth){
        /*TODO: Create RoomEntity, Get rooms from this entity*/
        //Collection<Room> rooms = new ArrayList<>();
        String about = "TODO: Prepei na to anaktoume kapos ayto";
        ProfileEntity profileEntity= this.getProfileEntity();
        return new Host(profileEntity == null || depth == 1 ? null : profileEntity.toModel(), this.getUsername(), this.getPassword(), this.getEmail(), this.confirmed);
    }

    @Override
    public String toString() {
        return "HostEntity{" +
                "about='" + about + '\'' +
                ", confirmed=" + confirmed +
                "} " + super.toString();
    }
}
