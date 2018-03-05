package ted.rental.model.inputs;


import java.util.Date;

public class CalendarEntry {

    private Date available_from;
    private Date available_to;
    private double price;

    public CalendarEntry() {
    }

    public CalendarEntry(Date available_from, Date available_to, double price) {
        this.available_from = available_from;
        this.available_to = available_to;
        this.price = price;
    }

    public Date getAvailable_from() {
        return available_from;
    }

    public void setAvailable_from(Date available_from) {
        this.available_from = available_from;
    }

    public Date getAvailable_to() {
        return available_to;
    }

    public void setAvailable_to(Date available_to) {
        this.available_to = available_to;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
