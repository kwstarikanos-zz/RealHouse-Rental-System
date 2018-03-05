package ted.rental.model;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class Booking {

    private Integer id;

    @NotNull
    private Reservation reservation;

    @NotNull
    private Date checkInDate;

    @NotNull
    private Date checkOutDate;

    private Date created;

    public Booking() {
    }

    public Booking(Reservation reservation, Date checkInDate, Date checkOutDate, Date created) {
        this.reservation = reservation;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
