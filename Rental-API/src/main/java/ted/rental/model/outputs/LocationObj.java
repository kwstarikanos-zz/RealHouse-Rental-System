package ted.rental.model.outputs;


public class LocationObj {

    private double latitude;

    private double longitude;

    public LocationObj() {
    }

    public LocationObj(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "LocationObj{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
