package ted.rental.database.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RoomTransportId implements Serializable{

    @Column(name = "room_id", nullable = false)
    private Integer room_id;

    @Column(name = "transport_id", nullable = false)
    private Integer transport_id;

    public Integer getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Integer room_id) {
        this.room_id = room_id;
    }

    public Integer getTransport_id() {
        return transport_id;
    }

    public void setTransport_id(Integer transport_id) {
        this.transport_id = transport_id;
    }

    @Override
    public String toString() {
        return "RoomTransportId{" +
                "room_id=" + room_id +
                ", transport_id=" + transport_id +
                '}';
    }
}
