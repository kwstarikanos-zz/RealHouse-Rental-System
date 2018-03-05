package ted.rental.database.entities;

import ted.rental.model.*;
import ted.rental.model.outputs.ResidenceObj;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Entity
@NamedQueries({
        @NamedQuery(name = "rooms.findAll", query = "SELECT r FROM RoomEntity r ORDER BY r.overnight_price ASC"),
        @NamedQuery(name = "rooms.findById", query = "SELECT r FROM RoomEntity r where r.id = :id"),
        @NamedQuery(name = "rooms.findRoomsByOwner", query = "Select r from RoomEntity r WHERE r.hostEntity.username = :username"),


        @NamedQuery(name = "rooms.findByLocationAndBothDates", query = "SELECT r FROM RoomEntity r WHERE ((r.locationEntity.locality = :locality AND r.locationEntity.country =:country) OR r.locationEntity.administrative_area_level_1 =:locality OR r.locationEntity.administrative_area_level_2 =:locality OR r.locationEntity.administrative_area_level_3 =:locality OR r.locationEntity.administrative_area_level_4 =:locality OR r.locationEntity.administrative_area_level_5 =:locality ) and r.max_people>=:guests and r.id in (SELECT c.calendarId.room_id FROM CalendarEntity c WHERE c.available = true and c.calendarId.date BETWEEN :checkin and :checkout GROUP BY c.calendarId.room_id HAVING count(c.calendarId.room_id) >= :noOfDays) ORDER BY r.overnight_price ASC"),
        @NamedQuery(name = "rooms.findByLocationAndSingleDate", query = "SELECT r FROM RoomEntity r WHERE ((r.locationEntity.locality = :locality AND r.locationEntity.country =:country) OR r.locationEntity.administrative_area_level_1 =:locality OR r.locationEntity.administrative_area_level_2 =:locality OR r.locationEntity.administrative_area_level_3 =:locality OR r.locationEntity.administrative_area_level_4 =:locality OR r.locationEntity.administrative_area_level_5 =:locality ) and r.max_people>=:guests and r.id in (select c.calendarId.room_id from CalendarEntity c where c.calendarId.date =:date and c.available=true) ORDER BY r.overnight_price ASC"),
        @NamedQuery(name = "rooms.findByCountryAndBothDates", query = "SELECT r FROM RoomEntity r WHERE (r.locationEntity.country = :country) and r.max_people>=:guests and r.id in (SELECT c.calendarId.room_id FROM CalendarEntity c WHERE c.available = true and c.calendarId.date BETWEEN :checkin and :checkout GROUP BY c.calendarId.room_id HAVING count(c.calendarId.room_id) >= :noOfDays) ORDER BY r.overnight_price ASC"),
        @NamedQuery(name = "rooms.findByCountryAndSingleDate", query = "SELECT r from RoomEntity r WHERE r.locationEntity.country = :country AND r.max_people>= :guests and r.id in (select c.calendarId.room_id from CalendarEntity c where c.calendarId.date =:date and c.available=true)ORDER BY r.overnight_price ASC"),
        @NamedQuery(name = "rooms.findByBothDates", query = "SELECT r from RoomEntity  r where r.id in (SELECT c.calendarId.room_id FROM CalendarEntity c WHERE c.available = true and c.calendarId.date BETWEEN :checkin and :checkout GROUP BY c.calendarId.room_id HAVING count(c.calendarId.room_id) >= :noOfDays) and r.max_people>= :guests ORDER BY r.overnight_price ASC"),
        @NamedQuery(name = "rooms.findBySingleDate", query = "SELECT r from RoomEntity r where r.id in (select c.calendarId.room_id from CalendarEntity c where c.calendarId.date =:date and c.available=true) and r.max_people>= :guests ORDER BY r.overnight_price ASC"),
        @NamedQuery(name = "rooms.findByLocalityAndCountry", query = "Select r from RoomEntity r WHERE ((r.locationEntity.locality = :locality AND r.locationEntity.country =:country) OR r.locationEntity.administrative_area_level_1 =:locality OR r.locationEntity.administrative_area_level_2 =:locality OR r.locationEntity.administrative_area_level_3 =:locality OR r.locationEntity.administrative_area_level_4 =:locality OR r.locationEntity.administrative_area_level_5 =:locality ) and r.max_people>=:guests ORDER BY r.overnight_price ASC"),
        @NamedQuery(name = "rooms.findByCountry", query = "SELECT r from RoomEntity r WHERE r.locationEntity.country = :country AND r.max_people>= :guests ORDER BY r.overnight_price ASC"),
        @NamedQuery(name = "rooms.findByNumberOfGuests", query = "SELECT r from RoomEntity r WHERE r.max_people>= :guests ORDER BY r.overnight_price ASC"),

        @NamedQuery(name = "rooms.findCountByLocationAndBothDates", query = "Select COUNT (r.id) from RoomEntity r where ((r.locationEntity.locality = :locality AND r.locationEntity.country =:country) OR r.locationEntity.administrative_area_level_1 =:locality OR r.locationEntity.administrative_area_level_2 =:locality OR r.locationEntity.administrative_area_level_3 =:locality OR r.locationEntity.administrative_area_level_4 =:locality OR r.locationEntity.administrative_area_level_5 =:locality ) and r.max_people>=:guests and r.id in (SELECT c.calendarId.room_id FROM CalendarEntity c WHERE c.available = true and c.calendarId.date BETWEEN :checkin and :checkout GROUP BY c.calendarId.room_id HAVING count(c.calendarId.room_id) >= :noOfDays)"),
        @NamedQuery(name = "rooms.findCountByLocationAndSingleDate", query = "SELECT COUNT (r.id) from RoomEntity r where ((r.locationEntity.locality = :locality AND r.locationEntity.country =:country) OR r.locationEntity.administrative_area_level_1 =:locality OR r.locationEntity.administrative_area_level_2 =:locality OR r.locationEntity.administrative_area_level_3 =:locality OR r.locationEntity.administrative_area_level_4 =:locality OR r.locationEntity.administrative_area_level_5 =:locality ) and r.max_people>=:guests and r.id in (select c.calendarId.room_id from CalendarEntity c where c.calendarId.date =:date and c.available=true)"),
        @NamedQuery(name = "rooms.findCountByCountryAndBothDates", query = "select COUNT (r.id) from RoomEntity r where (r.locationEntity.country = :country) and r.max_people>=:guests and r.id in (SELECT c.calendarId.room_id FROM CalendarEntity c WHERE c.available = true and c.calendarId.date BETWEEN :checkin and :checkout GROUP BY c.calendarId.room_id HAVING count(c.calendarId.room_id) >= :noOfDays)"),
        @NamedQuery(name = "rooms.findCountByCountryAndSingleDate", query = "SELECT COUNT (r.id) from RoomEntity r where r.locationEntity.country = :country AND r.max_people>= :guests and r.id in (select c.calendarId.room_id from CalendarEntity c where c.calendarId.date =:date and c.available=true)"),
        @NamedQuery(name = "rooms.findCountByBothDates", query = "SELECT COUNT (r.id) from RoomEntity r where r.id in (SELECT c.calendarId.room_id FROM CalendarEntity c WHERE c.available = true and c.calendarId.date BETWEEN :checkin and :checkout GROUP BY c.calendarId.room_id HAVING count(c.calendarId.room_id) >= :noOfDays) and r.max_people>= :guests"),
        @NamedQuery(name = "rooms.findCountBySingleDate", query = "SELECT COUNT (r.id) from RoomEntity r where r.id in (select c.calendarId.room_id from CalendarEntity c where c.calendarId.date =:date and c.available=true) and r.max_people>= :guests"),
        @NamedQuery(name = "rooms.findCountByLocalityAndCountry", query = "SELECT COUNT (r.id) from RoomEntity r where ((r.locationEntity.locality = :locality AND r.locationEntity.country =:country) OR r.locationEntity.administrative_area_level_1 =:locality OR r.locationEntity.administrative_area_level_2 =:locality OR r.locationEntity.administrative_area_level_3 =:locality OR r.locationEntity.administrative_area_level_4 =:locality OR r.locationEntity.administrative_area_level_5 =:locality ) and r.max_people>=:guests"),
        @NamedQuery(name = "rooms.findCountByCountry", query = "SELECT COUNT(r.id) from RoomEntity r WHERE r.locationEntity.country = :country AND r.max_people>= :guests"),
        @NamedQuery(name = "rooms.findCountByNumberOfGuests", query = "SELECT COUNT (r.id) from RoomEntity r WHERE r.max_people>= :guests"),

        @NamedQuery(name = "rooms.deleteById", query = "DELETE FROM RoomEntity r WHERE r.id = :id")
})
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Basic
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Basic
    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Basic
    @Column(name = "square_meters", nullable = false)
    private Integer square_meters;

    @Basic
    @Column(name = "overnight_price", nullable = false)
    private double overnight_price;

    @Basic
    @Column(name = "exta_person_price", nullable = false)
    private double exta_person_price;

    @Basic
    @Column(name = "max_people", nullable = false)
    private Integer max_people;

    @Basic
    @Column(name = "min_overnights", nullable = false)
    private Integer min_overnights;

    @Basic
    @Column(name = "beds", nullable = false)
    private Integer beds;

    @Basic
    @Column(name = "bathrooms", nullable = false)
    private Integer bathrooms;

    @Basic
    @Column(name = "bedrooms", nullable = false)
    private Integer bedrooms;

    @Basic
    @Column(name = "transport", length = 300)
    private String transport;

    @Basic
    @Column(name = "neightborhood", length = 300)
    private String neightborhood;

    @Basic
    @Column(name = "house_rules", length = 200)
    private String house_rules;


    /*                                                                           */
      /*                  Σχέση  Πολλά-Προς-Ένα ((Room))-((Host))                  */
     /*                                                                           */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "room_host", referencedColumnName = "username")
    private HostEntity hostEntity;

    public HostEntity getHostEntity() {
        return hostEntity;
    }

    public void setHostEntity(HostEntity hostEntity) {
        this.hostEntity = hostEntity;
    }


    /*                                                                           */
      /*                 Σχέση Ένα-Προς-Ένα ((Room))-((Type))                      */
     /*                                                                           */
    @OneToOne
    @JoinColumn(name = "room_type_id", referencedColumnName = "id")
    private TypeEntity typeEntity;

    public TypeEntity getTypeEntity() {
        return typeEntity;
    }

    public void setTypeEntity(TypeEntity typeEntity) {
        this.typeEntity = typeEntity;
    }


    /*                                                                           */
     /*                 Σχέση Ένα-Προς-Ένα ((Room))-((Location))                  */
    /*                                                                           */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_location", referencedColumnName = "id")
    private LocationEntity locationEntity;

    public LocationEntity getLocationEntity() {
        return locationEntity;
    }

    public void setLocationEntity(LocationEntity locationEntity) {
        this.locationEntity = locationEntity;
    }

    /*                                                                           */
      /*                 Σχέση Ένα-Προς-Πολλά ((Room))-((Pictures))                */
     /*                                                                           */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ROOMENTITY_PICTUREENTITY",
            joinColumns = {@JoinColumn(name = "room_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "picture_id", referencedColumnName = "id", unique = true)}
    )
    private Collection<PictureEntity> pictureEntities = new HashSet<>();

    public Collection<PictureEntity> getPictureEntities() {
        return pictureEntities;
    }

    public void setPictureEntities(Collection<PictureEntity> pictureEntities) {
        this.pictureEntities = pictureEntities;
    }


    /*                                                                           */
      /*                 Σχέση Ένα-Προς-Πολλά ((Room))-((Amenities))               */
     /*                                                                           */
    @OneToMany
    @JoinTable(
            name = "ROOMENTITY_AMENITIESENTITY",
            joinColumns = {@JoinColumn(name = "room_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "amenity_id", referencedColumnName = "id")}
    )
    private Collection<AmenitiesEntity> amenitiesEntities = new HashSet<>();

    public Collection<AmenitiesEntity> getAmenitiesEntities() {
        return amenitiesEntities;
    }

    public void setAmenitiesEntities(Collection<AmenitiesEntity> amenitiesEntities) {
        this.amenitiesEntities = amenitiesEntities;
    }


       /*                                                                           */
      /*                 Σχέση Ένα-Προς-Πολλά ((Room))-((Rules))                   */
     /*                                                                           */
    @OneToMany
    @JoinTable(
            name = "ROOMENTITY_RULEENTITY",
            joinColumns = {@JoinColumn(name = "room_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "rule_id", referencedColumnName = "id")}
    )
    private Collection<RuleEntity> ruleEntities = new HashSet<>();

    public Collection<RuleEntity> getRuleEntities() {
        return ruleEntities;
    }

    public void setRuleEntities(Collection<RuleEntity> ruleEntities) {
        this.ruleEntities = ruleEntities;
    }


    /*                                                                           */
      /*                 Σχέση Ένα-Προς-Πολλά ((Room))-((Rules))                   */
     /*                                                                           */
    @OneToMany
    @JoinTable(
            name = "ROOMENTITY_TRANSPORTENTITY",
            joinColumns = {@JoinColumn(name = "room_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "transport_id", referencedColumnName = "id")}
    )
    private Collection<TransportEntity> transportEntities = new HashSet<>();

    public Collection<TransportEntity> getTransportEntities() {
        return transportEntities;
    }

    public void setTransportEntities(Collection<TransportEntity> transportEntities) {
        this.transportEntities = transportEntities;
    }

    /*                                                                           */
      /*                 Σχέση Ένα-Προς-Πολλά ((Room))-((Calendar))                */
     /*                                                                           */
    @OneToMany(mappedBy = "roomEntity", cascade = CascadeType.ALL)
    private Collection<CalendarEntity> calendarEntities = new HashSet<>();

    public Collection<CalendarEntity> getCalendarEntities() {
        return calendarEntities;
    }

    public void setCalendarEntities(Collection<CalendarEntity> calendarEntities) {
        this.calendarEntities = calendarEntities;
    }

    /*                                                                           */
      /*                 Σχέση Ένα-Προς-Πολλά ((Room))-((Reviews))                 */
     /*                                                                           */
    @OneToMany(mappedBy = "roomEntity", cascade = CascadeType.ALL)
    private Collection<ReviewEntity> reviewEntities = new HashSet<>();

    public Collection<ReviewEntity> getReviewEntities() {
        return reviewEntities;
    }

    public void setReviewEntities(Collection<ReviewEntity> reviewEntities) {
        this.reviewEntities = reviewEntities;
    }

    public RoomEntity() {

    }

    public RoomEntity(String title, String description, Integer square_meters,
                      double overnight_price, double exta_person_price, Integer max_people,
                      Integer min_overnights, Integer beds, Integer bathrooms, Integer bedrooms,
                      String transport, String neightborhood, String rules, HostEntity hostEntity,
                      TypeEntity typeEntity, LocationEntity locationEntity, Collection<PictureEntity> pictureEntities,
                      Collection<AmenitiesEntity> amenitiesEntities, Collection<RuleEntity> ruleEntities) {
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
        this.house_rules = rules;
        this.hostEntity = hostEntity;
        this.typeEntity = typeEntity;
        this.locationEntity = locationEntity;
        this.pictureEntities = pictureEntities;
        this.amenitiesEntities = amenitiesEntities;
        this.ruleEntities = ruleEntities;
    }

    public RoomEntity(String title, String description, Integer square_meters, double overnight_price, double exta_person_price, Integer max_people, Integer min_overnights,
                      Integer beds, Integer bathrooms, Integer bedrooms, String transport, String neightborhood, String rules) {
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
        this.house_rules = rules;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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


    private Collection<Picture> toPicturesModel() {
        Collection<Picture> pictures = new ArrayList<>();
        for (PictureEntity o : this.pictureEntities) {
            pictures.add(o.toModel());
        }
        return pictures;
    }

    private Collection<Amenities> toAmenitiesModel() {
        Collection<Amenities> amenities = new ArrayList<>();
        for (AmenitiesEntity o : this.amenitiesEntities) {
            amenities.add(o.toModel());
        }
        return amenities;
    }

    private Collection<Rule> toRulesModel() {
        Collection<Rule> rules = new ArrayList<>();
        for (RuleEntity o : this.ruleEntities) {
            rules.add(o.toModel());
        }
        return rules;
    }

    private Collection<Calendar> toCalendarModel() {
        Collection<Calendar> calendars = new ArrayList<>();
        for (CalendarEntity o : this.calendarEntities) {
            calendars.add(o.toModel());
        }
        return calendars;
    }

    private Collection<Review> toReviewModel() {
        Collection<Review> reviews = new ArrayList<>();
        for (ReviewEntity review : this.reviewEntities) {
            reviews.add(review.toModel());
        }
        return reviews;
    }

    @Override
    public String toString() {
        return "RoomEntity{" +
                "id=" + id +
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
                ", hostEntity=" + hostEntity +
                ", typeEntity=" + typeEntity +
                ", locationEntity=" + locationEntity +
                ", pictureEntities=" + pictureEntities +
                ", amenitiesEntities=" + amenitiesEntities +
                ", ruleEntities=" + ruleEntities +
                ", calendarEntity=" + calendarEntities +
                '}';
    }

    public Room toModel(Integer depth) {
        return new Room(this.id,
                this.hostEntity == null ? null : this.hostEntity.toModel(depth),
                this.getTypeEntity() == null ? null : this.typeEntity.toModel(),
                this.getLocationEntity() == null ? null : this.locationEntity.toModel(),
                this.getPictureEntities() == null ? null : this.toPicturesModel(),
                this.getAmenitiesEntities() == null ? null : this.toAmenitiesModel(),
                this.getRuleEntities() == null ? null : this.toRulesModel(),
                this.title, this.description, this.square_meters,
                this.overnight_price, this.exta_person_price,
                this.max_people, this.min_overnights, this.beds, this.bathrooms,
                this.bedrooms, this.transport, this.neightborhood, this.house_rules);
    }

    public ResidenceObj toResidenceObj() {
        PictureEntity profilePicture = this.hostEntity.getProfileEntity().getPictureEntity();
        return new ResidenceObj(
                this.id,
                this.hostEntity.getProfileEntity().getOwner(),
                profilePicture == null ? null : profilePicture.toModel(),
                this.getTypeEntity() == null ? null : this.typeEntity.toModel(),
                this.getLocationEntity() == null ? null : this.locationEntity.toModel(),
                this.getPictureEntities() == null ? null : this.toPicturesModel(),
                this.getAmenitiesEntities() == null ? null : this.toAmenitiesModel(),
                this.getRuleEntities() == null ? null : this.toRulesModel(),
                this.getCalendarEntities() == null ? null : this.toCalendarModel(),
                this.getReviewEntities() == null ? null : this.toReviewModel(),
                this.title, this.description, this.square_meters,
                this.overnight_price, this.exta_person_price,
                this.max_people, this.min_overnights, this.beds, this.bathrooms,
                this.bedrooms, this.transport, this.neightborhood, this.house_rules
        );
    }
}
