package ted.rental.database.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
public class RoomPictureId implements Serializable{

    @Column(name = "room_id", nullable = false)
    private Integer room_id;

    @Column(name = "picture_id", nullable = false, unique = true)
    private Integer picture_id;

    public RoomPictureId() {
    }

    public RoomPictureId(Integer room_id, Integer picture_id) {
        this.room_id = room_id;
        this.picture_id = picture_id;
    }

    public Integer getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Integer room_id) {
        this.room_id = room_id;
    }

    public Integer getPicture_id() {
        return picture_id;
    }

    public void setPicture_id(Integer picture_id) {
        this.picture_id = picture_id;
    }

    @Override
    public String toString() {
        return "RoomPictureId{" +
                "room_id=" + room_id +
                ", picture_id=" + picture_id +
                '}';
    }
}
