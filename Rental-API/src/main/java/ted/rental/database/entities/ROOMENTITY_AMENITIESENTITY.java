package ted.rental.database.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ROOMENTITY_AMENITIESENTITY")
public class ROOMENTITY_AMENITIESENTITY implements Serializable{

    @EmbeddedId
    private RoomAmenityId roomAmenityId = new RoomAmenityId();

       /*                                                                           */
      /*          Σχέση  Πολλά-Προς-Ένα ((ROOMENTITY_AMENITIESENTITY))-((Room))    */
     /*                                                                           */
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RoomEntity roomEntity;

    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }

    /*                                                                          */
      /*   Σχέση  Πολλά-Προς-Ένα ((ROOMENTITY_AMENITIESENTITY))-((Amenitiy))      */
     /*                                                                          */
    @OneToOne
    @JoinColumn(name = "amenity_id", referencedColumnName = "id", insertable = false, updatable = false)
    private AmenitiesEntity amenitiesEntity;

    public AmenitiesEntity getAmenitiesEntity() {
        return amenitiesEntity;
    }

    public void setAmenitiesEntity(AmenitiesEntity amenitiesEntity) {
        this.amenitiesEntity = amenitiesEntity;
    }

    public ROOMENTITY_AMENITIESENTITY() {
    }

    public ROOMENTITY_AMENITIESENTITY(Integer room_id, Integer amenity_id) {
            this.roomAmenityId.setAmenity_id(amenity_id);
            this.roomAmenityId.setRoom_id(room_id);
    }

    public RoomAmenityId getRoomAmenityId() {
        return roomAmenityId;
    }

    public void setRoomAmenityId(RoomAmenityId roomAmenityId) {
        this.roomAmenityId = roomAmenityId;
    }


    @Override
    public String toString() {
        return "ROOMENTITY_AMENITIESENTITY{" +
                "roomAmenityId=" + roomAmenityId +
                ", roomEntity=" + roomEntity +
                ", amenitiesEntity=" + amenitiesEntity +
                '}';
    }
}
