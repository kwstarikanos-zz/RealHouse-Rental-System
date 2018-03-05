package ted.rental.database.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
public class RoomAmenityId implements Serializable{

    @Column(name = "room_id", nullable = false)
    private Integer room_id;

    @Column(name = "amenity_id", nullable = false)
    private Integer amenity_id;

    public Integer getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Integer room_id) {
        this.room_id = room_id;
    }

    public Integer getAmenity_id() {
        return amenity_id;
    }

    public void setAmenity_id(Integer amenity_id) {
        this.amenity_id = amenity_id;
    }

    @Override
    public String toString() {
        return "RoomAmenityId{" +
                "room_id=" + room_id +
                ", amenity_id=" + amenity_id +
                '}';
    }
}
