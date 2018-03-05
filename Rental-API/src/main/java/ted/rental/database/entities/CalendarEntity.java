package ted.rental.database.entities;


import ted.rental.model.Calendar;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@NamedQueries({
        @NamedQuery(name = "calendar.findByRoomId", query = "SELECT c FROM CalendarEntity c WHERE c.calendarId.room_id =:room_id"),
        @NamedQuery(name = "calendar.findByDate", query = "SELECT  c from CalendarEntity c where c.calendarId.date =:date"),
        @NamedQuery(name = "calendar.findByDateAndRoomId", query = "SELECT c from CalendarEntity c WHERE  c.calendarId.date =:date AND c.calendarId.room_id =:room_id"),
        @NamedQuery(name = "calendar.deleteByDateAndRoomId", query = "DELETE FROM CalendarEntity c WHERE c.calendarId.date =:date AND c.calendarId.room_id =:room_id")

})

public class CalendarEntity implements Serializable{

    @EmbeddedId
    private CalendarId calendarId = new CalendarId();

    @Basic
    @Column(name = "available", nullable = false)
    private Boolean available;

    @Basic
    @Column(name = "price")
    private double price;


       /*                                                                           */
      /*                  Σχέση  Πολλά-Προς-Ένα ((Calendar))-((Room))              */
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


    @ManyToOne
    @JoinColumn(name = "renter_username", referencedColumnName = "username")
    private RenterEntity renterEntity;

    public RenterEntity getRenterEntity() {
        return renterEntity;
    }

    public void setRenterEntity(RenterEntity renterEntity) {
        this.renterEntity = renterEntity;
    }

    public CalendarEntity() {
    }


    public CalendarEntity(Integer room_id, Date date, Boolean available, double price, RoomEntity roomEntity) {
        this.calendarId.setDate(date);
        this.calendarId.setRoom_id(room_id);
        this.available = available;
        this.price = price;
        this.roomEntity = roomEntity;
    }

    public CalendarId getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(CalendarId calendarId) {
        this.calendarId = calendarId;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CalendarEntity{" +
                "calendarId=" + calendarId +
                ", available=" + available +
                ", price=" + price +
                ", roomEntity=" + roomEntity +
                ", renterEntity=" + renterEntity +
                '}';
    }

    public Calendar toModel() {
        return new Calendar(this.getCalendarId().getRoom_id(), this.getCalendarId().getDate(), this.available, this.price);
    }
}
