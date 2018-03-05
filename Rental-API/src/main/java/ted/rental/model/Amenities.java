package ted.rental.model;


import ted.rental.database.entities.AmenitiesEntity;

import javax.validation.constraints.NotNull;

public class Amenities {

    @NotNull
    private Integer id;

    private String amenity;

    public Amenities() {
    }

    public Amenities(Integer id, String amenity) {
        this.id = id;
        this.amenity = amenity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAmenity() {
        return amenity;
    }

    public void setAmenity(String amenity) {
        this.amenity = amenity;
    }

    @Override
    public String toString() {
        return "Amenities{" +
                "id=" + id +
                ", amenity='" + amenity + '\'' +
                '}';
    }

    public AmenitiesEntity toEntity(){
        return new AmenitiesEntity(this.id, this.amenity);
    }
}
