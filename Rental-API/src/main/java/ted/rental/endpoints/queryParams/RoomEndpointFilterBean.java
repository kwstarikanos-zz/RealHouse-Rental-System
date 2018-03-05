package ted.rental.endpoints.queryParams;


import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public class RoomEndpointFilterBean {

    private @QueryParam("owner")
    String owner;

    private @QueryParam("start")
    int start;

    private @QueryParam("size")
    int size;

    private @QueryParam("type")
    @DefaultValue("0")
    Integer type;

    private @QueryParam("beds")
    @DefaultValue("1")
    Integer beds;

    private @QueryParam("min_price")
    Integer min_price;

    private @QueryParam("max_price")
    Integer max_price;

    private @QueryParam("depth")
    int depth;

    private @QueryParam("locality")
    String locality;

    private @QueryParam("country")
    String country;

    private @QueryParam("checkin")
    String checkinDate;

    private @QueryParam("checkout")
    String checkoutDate;

    private @QueryParam("guests") //@DefaultValue("1")
    Integer guests;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBeds() {
        return beds;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public Integer getMin_price() {
        return min_price;
    }

    public void setMin_price(Integer min_price) {
        this.min_price = min_price;
    }

    public Integer getMax_price() {
        return max_price;
    }

    public void setMax_price(Integer max_price) {
        this.max_price = max_price;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Integer getGuests() {
        return guests;
    }

    public void setGuests(Integer guests) {
        this.guests = guests;
    }

    @Override
    public String toString() {
        return "RoomEndpointFilterBean{" +
                "owner='" + owner + '\'' +
                ", start=" + start +
                ", size=" + size +
                ", type=" + type +
                ", beds=" + beds +
                ", min_price=" + min_price +
                ", max_price=" + max_price +
                ", depth=" + depth +
                ", locality='" + locality + '\'' +
                ", country='" + country + '\'' +
                ", checkinDate='" + checkinDate + '\'' +
                ", checkoutDate='" + checkoutDate + '\'' +
                ", guests=" + guests +
                '}';
    }
}
