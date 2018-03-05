package ted.rental.database.entities;

import ted.rental.model.Location;

import javax.persistence.*;

import java.io.Serializable;

import static ted.rental.config.Constraint.*;

@Entity
public class LocationEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "id", nullable =false, unique = true)
    private Integer id;

    @Basic
    @Column(name = "latitude", nullable = false, precision = 0)
    private double latitude;

    @Basic
    @Column(name = "longitude", nullable = false, precision = 0)
    private double longitude;

    @Basic
    @Column(name = "street_number", length = STREET_MAX)
    private String street_number;

    @Basic
    @Column(name= "route", length =  ROUTE_MAX)
    private String route;

    @Basic
    @Column(name = "locality", length = LOCALITY_MAX)
    private String locality;

    @Basic
    @Column(name = "postal_code", length = POSTAL_CODE_MAX)
    private String postal_code;

    @Basic
    @Column(name = "country", length = COUNTRY_MAX)
    private String country;

    @Basic
    @Column(name = "administrative_area_level_5", length = ADMINISTRATIVE_AREA_LEVEL_MAX)
    private String administrative_area_level_5;

    @Basic
    @Column(name = "administrative_area_level_4", length = ADMINISTRATIVE_AREA_LEVEL_MAX)
    private String administrative_area_level_4;

    @Basic
    @Column(name = "administrative_area_level_3", length = ADMINISTRATIVE_AREA_LEVEL_MAX)
    private String administrative_area_level_3;

    @Basic
    @Column(name = "administrative_area_level_2", length = ADMINISTRATIVE_AREA_LEVEL_MAX)
    private String administrative_area_level_2;

    @Basic
    @Column(name = "administrative_area_level_1", length = ADMINISTRATIVE_AREA_LEVEL_MAX)
    private String administrative_area_level_1;

    @Basic
    @Column(name = "formatted_address", length = FORMATTED_ADRESS_MAX, nullable = false)
    private String formatted_address;

    public LocationEntity() {
        super();
    }


    public LocationEntity(double latitude, double longitude, String street_number, String route,
                          String locality, String postal_code, String country,
                          String administrative_area_level_5, String administrative_area_level_4, String administrative_area_level_3,
                          String administrative_area_level_2, String administrative_area_level_1, String formatted_address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.street_number = street_number;
        this.route = route;
        this.locality = locality;
        this.postal_code = postal_code;
        this.country = country;
        this.administrative_area_level_5 = administrative_area_level_5;
        this.administrative_area_level_4 = administrative_area_level_4;
        this.administrative_area_level_3 = administrative_area_level_3;
        this.administrative_area_level_2 = administrative_area_level_2;
        this.administrative_area_level_1 = administrative_area_level_1;
        this.formatted_address = formatted_address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getStreet_number() {
        return street_number;
    }

    public void setStreet_number(String street_number) {
        this.street_number = street_number;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String city) {
        this.locality = city;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAdministrative_area_level_5() {
        return administrative_area_level_5;
    }

    public void setAdministrative_area_level_5(String administrative_area_level_5) {
        this.administrative_area_level_5 = administrative_area_level_5;
    }

    public String getAdministrative_area_level_4() {
        return administrative_area_level_4;
    }

    public void setAdministrative_area_level_4(String administrative_area_level_4) {
        this.administrative_area_level_4 = administrative_area_level_4;
    }

    public String getAdministrative_area_level_3() {
        return administrative_area_level_3;
    }

    public void setAdministrative_area_level_3(String administrative_area_level_3) {
        this.administrative_area_level_3 = administrative_area_level_3;
    }

    public String getAdministrative_area_level_2() {
        return administrative_area_level_2;
    }

    public void setAdministrative_area_level_2(String administrative_area_level_2) {
        this.administrative_area_level_2 = administrative_area_level_2;
    }

    public String getAdministrative_area_level_1() {
        return administrative_area_level_1;
    }

    public void setAdministrative_area_level_1(String administrative_area_level_1) {
        this.administrative_area_level_1 = administrative_area_level_1;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }


    public Location toModel() {
        return new Location(this.latitude, this.longitude, this.street_number, this.route, this.locality, this.postal_code,
                this.country, this.administrative_area_level_5, this.administrative_area_level_4, this.administrative_area_level_3,
                this.administrative_area_level_2, this.administrative_area_level_1, this.formatted_address);
    }

}
