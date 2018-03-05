package ted.rental.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.Length;
import ted.rental.database.entities.LocationEntity;
import javax.validation.constraints.NotNull;

import static ted.rental.config.Constraint.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Location {

    private Integer id;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    @Length(max = STREET_MAX)
    private String street_number;

    @Length(max = ROUTE_MAX)
    private String route;

    @Length(max = LOCALITY_MAX)
    private String locality;

    @Length(max = POSTAL_CODE_MAX)
    private String postal_code;

    @Length(max = COUNTRY_MAX)
    private String country;

    @Length(max = ADMINISTRATIVE_AREA_LEVEL_MAX)
    private String administrative_area_level_5;

    @Length(max = ADMINISTRATIVE_AREA_LEVEL_MAX)
    private String administrative_area_level_4;

    @Length(max = ADMINISTRATIVE_AREA_LEVEL_MAX)
    private String administrative_area_level_3;

    @Length(max = ADMINISTRATIVE_AREA_LEVEL_MAX)
    private String administrative_area_level_2;

    @Length(max = ADMINISTRATIVE_AREA_LEVEL_MAX)
    private String administrative_area_level_1;

    @Length(max = FORMATTED_ADRESS_MAX)
    private String formatted_address;


    public Location() {

    }

    public Location(double latitude, double longitude, String street_number, String route, String locality,
                    String postal_code, String country, String administrative_area_level_5, String administrative_area_level_4,
                    String administrative_area_level_3, String administrative_area_level_2, String administrative_area_level_1,
                    String formatted_address) {
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

    public void setLocality(String locality) {
        this.locality = locality;
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


    @Override
    public String toString() {
        return "Location{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", street_number='" + street_number + '\'' +
                ", route='" + route + '\'' +
                ", locality='" + locality + '\'' +
                ", postal_code='" + postal_code + '\'' +
                ", country='" + country + '\'' +
                ", administrative_area_level_5='" + administrative_area_level_5 + '\'' +
                ", administrative_area_level_4='" + administrative_area_level_4 + '\'' +
                ", administrative_area_level_3='" + administrative_area_level_3 + '\'' +
                ", administrative_area_level_2='" + administrative_area_level_2 + '\'' +
                ", administrative_area_level_1='" + administrative_area_level_1 + '\'' +
                ", formatted_adress='" + formatted_address + '\'' +
                '}';
    }

    public LocationEntity toEntity() {
        return new LocationEntity(this.latitude, this.longitude, this.street_number,
                this.route, this.locality, this.postal_code, this.country,
                this.administrative_area_level_5, this.administrative_area_level_4, this.administrative_area_level_3,
                this.administrative_area_level_2, this.administrative_area_level_1, this.formatted_address);
    }

}