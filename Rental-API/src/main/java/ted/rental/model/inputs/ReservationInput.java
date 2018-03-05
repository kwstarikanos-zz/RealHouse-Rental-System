package ted.rental.model.inputs;

import ted.rental.database.entities.RenterEntity;
import ted.rental.database.entities.ReservationEntity;
import ted.rental.database.entities.RoomEntity;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class ReservationInput {

    @NotNull
    private Date checkInDate;

    @NotNull
    private Date checkOutDate;

    @NotNull
    private Integer guests;

    @NotNull
    private double total_cost;

    public ReservationInput() {
    }

    public ReservationInput(Date checkInDate, Date checkOutDate, Integer guests, double total_cost) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guests = guests;
        this.total_cost = total_cost;
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

    public double getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(double total_cost) {
        this.total_cost = total_cost;
    }

    public ReservationEntity toEntity(RoomEntity room, RenterEntity renter){
        return new ReservationEntity(this.getCheckInDate(), this.getCheckOutDate(),
                this.getGuests(), false, this.getTotal_cost(), room, renter);
    }
}


