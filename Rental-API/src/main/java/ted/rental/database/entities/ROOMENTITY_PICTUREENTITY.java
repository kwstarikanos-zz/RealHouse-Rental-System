package ted.rental.database.entities;

import org.eclipse.jetty.util.annotation.Name;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "roompicture.findByIds", query = "SELECT p from ROOMENTITY_PICTUREENTITY  p where p.roomPictureId.picture_id =:picture_id and p.roomPictureId.room_id = :room_id"),
        @NamedQuery(name = "roompicture.deleteById", query = "DELETE FROM ROOMENTITY_PICTUREENTITY rp where rp.roomPictureId.picture_id=:picture_id and rp.roomPictureId.room_id=:room_id")

})
@Table(name = "ROOMENTITY_PICTUREENTITY")
public class ROOMENTITY_PICTUREENTITY {

    @EmbeddedId
    private RoomPictureId roomPictureId = new RoomPictureId();



       /*                                                                           */
      /*          Σχέση  Πολλά-Προς-Ένα ((ROOMENTITY_PICTUREENTITY))-((Room))      */
     /*                                                                           */
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "room_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RoomEntity roomEntity;

    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }

       /*                                                                           */
      /*          Σχέση  Πολλά-Προς-Ένα ((ROOMENTITY_PICTUREENTITY))-((Picture))   */
     /*                                                                           */
    @OneToOne
    @JoinColumn(name = "picture_id", referencedColumnName = "id", insertable = false, updatable = false)
    private PictureEntity pictureEntity;

    public PictureEntity getPictureEntity() {
        return pictureEntity;
    }

    public void setPictureEntity(PictureEntity pictureEntity) {
        this.pictureEntity = pictureEntity;
    }

    public ROOMENTITY_PICTUREENTITY() {
    }

    public ROOMENTITY_PICTUREENTITY(Integer room_id, Integer picture_id) {
        this.roomPictureId.setRoom_id(room_id);
        this.roomPictureId.setPicture_id(picture_id);
    }

    public RoomPictureId getRoomPictureId() {
        return roomPictureId;
    }

    public void setRoomPictureId(RoomPictureId roomPictureId) {
        this.roomPictureId = roomPictureId;
    }

    @Override
    public String toString() {
        return "ROOMENTITY_PICTUREENTITY{" +
                "roomPictureId=" + roomPictureId +
                ", roomEntity=" + roomEntity +
                ", pictureEntity=" + pictureEntity +
                '}';
    }
}
