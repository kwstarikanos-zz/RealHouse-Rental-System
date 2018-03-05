package ted.rental.model.outputs;


import java.util.ArrayList;
import java.util.List;

public class ResidencesObj {

    private List<RoomObj> rooms = new ArrayList<>();
    private long count;
    private Double minPrice;
    private Double maxPrice;

    public ResidencesObj() {
    }

    public ResidencesObj(List<RoomObj> rooms, long count, Double minPrice, Double maxPrice) {
        this.rooms = rooms;
        this.count = count;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public List<RoomObj> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomObj> rooms) {
        this.rooms = rooms;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }
}
