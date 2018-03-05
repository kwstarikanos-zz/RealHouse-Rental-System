package ted.rental.model;

import org.hibernate.validator.constraints.Length;
import ted.rental.database.entities.ReservationEntity;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

public class Reservation {

    private Integer id;

    @NotNull
    private Integer room_id;

    @NotNull
    private String renter;

    @NotNull
    private Date checkInDate;

    @NotNull
    private Date checkOutDate;

    @NotNull
    private Integer guests;

    @NotNull
    private Boolean approved; //approved apo ton host

    @NotNull
    private Date created;

    private double total_cost;

    public Reservation() {
    }

    public Reservation(Integer id, Integer room_id, String renter,
                       Date checkInDate, Date checkOutDate,
                       Integer guests, Boolean approved, Date created, double total_cost) {
        this.id = id;
        this.room_id = room_id;
        this.renter = renter;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guests = guests;
        this.approved = approved;
        this.created = created;
        this.total_cost = total_cost;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Integer room_id) {
        this.room_id = room_id;
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

    public Integer getGuests() {
        return guests;
    }

    public void setGuests(Integer guests) {
        this.guests = guests;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getRenter() {
        return renter;
    }

    public void setRenter(String renter) {
        this.renter = renter;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public double getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(double total_cost) {
        this.total_cost = total_cost;
    }

    public ReservationEntity toEntity(){
        return new ReservationEntity();
    }
}
