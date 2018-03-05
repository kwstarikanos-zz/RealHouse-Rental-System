package ted.rental.model;


import javax.validation.constraints.NotNull;
import java.util.Date;

public class Calendar {

    private Integer room_id;

    @NotNull
    private Date date;

    @NotNull
    private Boolean available;

    private double price;

    private Renter renter;

    public Calendar() {
    }

    public Calendar(Integer room_id, Date date, Boolean available, double price) {
        this.room_id = room_id;
        this.date = date;
        this.available = available;
        this.price = price;
    }

    public Integer getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Integer room_id) {
        this.room_id = room_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Renter getRenter() {
        return renter;
    }

    public void setRenter(Renter renter) {
        this.renter = renter;
    }
}
