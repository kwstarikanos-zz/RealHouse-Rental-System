package ted.rental.database.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class CalendarId implements Serializable {

    @Column(name= "date", nullable = false)
    private Date date;

    @Column(name = "room_id", nullable = false)
    private Integer room_id;

    public CalendarId() {
    }

    public CalendarId(Date date, Integer room_id) {
        this.date = date;
        this.room_id = room_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Integer room_id) {
        this.room_id = room_id;
    }

    @Override
    public String toString() {
        return "CalendarId{" +
                "date=" + date +
                ", room_id=" + room_id +
                '}';
    }
}
