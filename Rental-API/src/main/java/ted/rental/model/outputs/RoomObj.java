package ted.rental.model.outputs;

import ted.rental.model.Amenities;
import ted.rental.model.Picture;
import ted.rental.model.Rule;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Collection;

public class RoomObj {

    private Integer id;

    private String owner;

    private String title;

    private String description;

    private String type;

    private double overnight_price;

    private Integer beds;

    Collection<Picture> pictures = new ArrayList<>();

    Collection<Amenities> amenities = new ArrayList<>();

    Collection<Rule> rules = new ArrayList<>();

    private LocationObj location = new LocationObj();

    private String link;

    private Integer reviews;
    //TODO Reviews

    private Integer ratingStars;

    public RoomObj() {

    }

    public RoomObj(Integer id, String owner, String title, String description, String type,
                   double overnight_price, double latitude, double longitude, Integer reviews, Integer ratingStars, Integer beds) {
        this.id = id;
        this.owner = owner;
        this.title = title;
        this.description = description;
        this.type = type;
        this.overnight_price = overnight_price;
        this.location.setLatitude(latitude);
        this.location.setLongitude(longitude);
        this.reviews = reviews;
        this.amenities = new ArrayList<>();
        this.rules = new ArrayList<>();
        this.pictures = new ArrayList<>();
        this.ratingStars = ratingStars;
        this.beds = beds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getOvernight_price() {
        return overnight_price;
    }

    public void setOvernight_price(double overnight_price) {
        this.overnight_price = overnight_price;
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

    public LocationObj getLocation() {
        return location;
    }

    public void setLocation(LocationObj location) {
        this.location = location;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getReviews() {
        return reviews;
    }

    public void setReviews(Integer reviews) {
        this.reviews = reviews;
    }

    public Integer getRatingStars() {
        return ratingStars;
    }

    public void setRatingStars(Integer ratingStars) {
        this.ratingStars = ratingStars;
    }

    public Integer getBeds() {
        return beds;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    @Override
    public String toString() {
        return "RoomObj{" +
                "id=" + id +
                ", owner='" + owner + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", overnight_price=" + overnight_price +
                ", beds=" + beds +
                ", pictures=" + pictures +
                ", amenities=" + amenities +
                ", rules=" + rules +
                ", location=" + location +
                ", link='" + link + '\'' +
                ", reviews=" + reviews +
                ", ratingStars=" + ratingStars +
                '}';
    }
}
