package ted.rental.database.entities;

import ted.rental.model.Amenities;

import javax.persistence.*;

@Entity
public class AmenitiesEntity {

    @Id
    @Column(name = "id", nullable =false, unique = true)
    private Integer id;

    @Basic
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String amenity;



       /*                                                                           */
      /*  Σχέση  Πολλά-Προς-Ένα ((AmenitiesEntity))-((ROOMENTITY_AMENITIESENTITY)) */
     /*                                                                           */
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "amenitiesEntity")
    private ROOMENTITY_AMENITIESENTITY roomentity_amenitiesentity;

    public ROOMENTITY_AMENITIESENTITY getRoomentity_amenitiesentity() {
        return roomentity_amenitiesentity;
    }

    public void setRoomentity_amenitiesentity(ROOMENTITY_AMENITIESENTITY roomentity_amenitiesentity) {
        this.roomentity_amenitiesentity = roomentity_amenitiesentity;
    }

    public AmenitiesEntity() {
    }

    public AmenitiesEntity(Integer id, String amenity) {
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

    public void setAmenity(String amenity_name) {
        this.amenity = amenity_name;
    }

    public Amenities toModel(){
        return new Amenities(this.id, this.amenity);
    }
}
