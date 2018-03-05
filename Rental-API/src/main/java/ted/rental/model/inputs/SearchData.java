package ted.rental.model.inputs;


import ted.rental.model.Location;

import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import java.util.Date;

public class SearchData {

    private Location location;

    private Date checkInDate;

    private Date checkOutDate;

    @NotNull
    @DefaultValue("1")
    private Integer guests;

    public SearchData() {
        location = new Location();
    }

    public SearchData(Location location, Date checkInDate, Date checkOutDate, Integer guests) {
        this.location = location;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guests = guests;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    @Override
    public String toString() {
        return "SearchData{" +
                "location=" + location +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", guests=" + guests +
                '}';
    }
}
