package ted.rental.model.outputs;

import org.hibernate.validator.constraints.Length;
import ted.rental.model.*;
import ted.rental.model.inputs.CalendarEntry;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;


public class ResidenceObj {

    private Integer id;

    @NotNull
    private ProfilePictureAndOwner profile = new ProfilePictureAndOwner();

    @NotNull
    private Type type;

    private Collection<Picture> pictures = new HashSet<>();

    private Collection<Amenities> amenities = new HashSet<>();

    private Collection<Rule> rules = new HashSet<>();

    private Collection<Calendar> calendars = new HashSet<>();

    private Collection<Review> reviews = new HashSet<>();

    @NotNull
    private Location location;

    @NotNull
    @Length(max = 45)
    private String title;

    @NotNull
    @Length(max = 300)
    private String description;

    @NotNull
    private Integer square_meters;

    @NotNull
    private double overnight_price;

    @NotNull
    private double exta_person_price;

    @NotNull
    private Integer max_people;

    @NotNull
    private Integer min_overnights;

    @NotNull
    private Integer beds;

    @NotNull
    private Integer bathrooms;

    @NotNull
    private Integer bedrooms;

    private String transport;

    private String neightborhood;

    @Length(max = 200)
    private String house_rules;


    /* Setup τους κανόνες των δωματίων.
    * Setup τις ανέσεις των δωματίων.
    * Τύπος δωματίου.
    * Εικόνες δωματίου.
    * Έγκριση αιτήσεων για ενοικίαση.
    * Προσθήκη διαθεσιμότητας των δωματίου.
    */


    public ResidenceObj() {

    }

    public ResidenceObj(Integer id, String owner, Picture picture, Type type, Location location, Collection<Picture> pictures,
                        Collection<Amenities> amenities, Collection<Rule> rules, Collection<Calendar> calendars, Collection<Review> reviews,
                        String title, String description, Integer square_meters, double overnight_price,
                        double exta_person_price, Integer max_people, Integer min_overnights, Integer beds,
                        Integer bathrooms, Integer bedrooms, String transport, String neightborhood, String house_rules) {
        this.id = id;
        this.profile.setOwner(owner);
        this.profile.setPicture(picture);
        this.type = type;
        this.location = location;
        this.pictures = pictures;
        this.amenities = amenities;
        this.calendars = calendars;
        this.reviews = reviews;
        this.rules = rules;
        this.title = title;
        this.description = description;
        this.square_meters = square_meters;
        this.overnight_price = overnight_price;
        this.exta_person_price = exta_person_price;
        this.max_people = max_people;
        this.min_overnights = min_overnights;
        this.beds = beds;
        this.bathrooms = bathrooms;
        this.bedrooms = bedrooms;
        this.transport = transport;
        this.neightborhood = neightborhood;
        this.house_rules = house_rules;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProfilePictureAndOwner getProfile() {
        return profile;
    }

    public void setProfile(ProfilePictureAndOwner profile) {
        this.profile = profile;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Collection<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Collection<Picture> pictures) {
        this.pictures = pictures;
    }

    public Collection<Amenities> getAmenities() {
        return amenities;
    }

    public void setAmenities(Collection<Amenities> amenities) {
        this.amenities = amenities;
    }

    public Collection<Rule> getRules() {
        return rules;
    }

    public void setRules(Collection<Rule> rules) {
        this.rules = rules;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSquare_meters() {
        return square_meters;
    }

    public void setSquare_meters(Integer square_meters) {
        this.square_meters = square_meters;
    }

    public double getOvernight_price() {
        return overnight_price;
    }

    public void setOvernight_price(double overnight_price) {
        this.overnight_price = overnight_price;
    }

    public double getExta_person_price() {
        return exta_person_price;
    }

    public void setExta_person_price(double exta_person_price) {
        this.exta_person_price = exta_person_price;
    }

    public Integer getMax_people() {
        return max_people;
    }

    public void setMax_people(Integer max_people) {
        this.max_people = max_people;
    }

    public Integer getMin_overnights() {
        return min_overnights;
    }

    public void setMin_overnights(Integer min_overnights) {
        this.min_overnights = min_overnights;
    }

    public Integer getBeds() {
        return beds;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getNeightborhood() {
        return neightborhood;
    }

    public void setNeightborhood(String neightborhood) {
        this.neightborhood = neightborhood;
    }

    public String getHouse_rules() {
        return house_rules;
    }

    public void setHouse_rules(String house_rules) {
        this.house_rules = house_rules;
    }

    public Collection<Calendar> getCalendars() {
        return calendars;
    }

    public void setCalendars(Collection<Calendar> calendars) {
        this.calendars = calendars;
    }

    public Collection<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "ResidenceObj{" +
                "id=" + id +
                ", profile=" + profile +
                ", type=" + type +
                ", pictures=" + pictures +
                ", amenities=" + amenities +
                ", rules=" + rules +
                ", calendars=" + calendars +
                ", reviews=" + reviews +
                ", location=" + location +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", square_meters=" + square_meters +
                ", overnight_price=" + overnight_price +
                ", exta_person_price=" + exta_person_price +
                ", max_people=" + max_people +
                ", min_overnights=" + min_overnights +
                ", beds=" + beds +
                ", bathrooms=" + bathrooms +
                ", bedrooms=" + bedrooms +
                ", transport='" + transport + '\'' +
                ", neightborhood='" + neightborhood + '\'' +
                ", house_rules='" + house_rules + '\'' +
                '}';
    }
}
