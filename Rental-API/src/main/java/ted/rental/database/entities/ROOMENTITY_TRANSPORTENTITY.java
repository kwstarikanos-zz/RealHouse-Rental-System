package ted.rental.database.entities;


import ted.rental.model.Transport;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ROOMENTITY_TRANSPORTENTITY")
public class ROOMENTITY_TRANSPORTENTITY implements Serializable{

    @EmbeddedId
    private RoomTransportId roomTransportId = new RoomTransportId();

       /*                                                                           */
      /*          Σχέση  Πολλά-Προς-Ένα ((ROOMENTITY_TRANSPORTENTITY))-((Room))    */
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
      /*   Σχέση  Πολλά-Προς-Ένα ((ROOMENTITY_TRANSPORTENTITY))-((Amenitiy))      */
     /*                                                                          */
    @OneToOne
    @JoinColumn(name = "transport_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TransportEntity transportEntity;

    public TransportEntity getTransportEntity() {
        return transportEntity;
    }

    public void setTransportEntity(TransportEntity transportEntity) {
        this.transportEntity = transportEntity;
    }

    public ROOMENTITY_TRANSPORTENTITY() {
    }

    public ROOMENTITY_TRANSPORTENTITY(RoomTransportId roomTransportId, RoomEntity roomEntity, AmenitiesEntity amenitiesEntity) {
        this.roomTransportId = roomTransportId;

    }

    public RoomTransportId getRoomTransportId() {
        return roomTransportId;
    }

    public void setRoomTransportId(RoomTransportId roomTransportId) {
        this.roomTransportId = roomTransportId;
    }


}
