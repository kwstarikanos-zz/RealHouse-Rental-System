package ted.rental.database.entities;

import ted.rental.model.Reservation;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "reservation.findById", query = "SELECT r FROM ReservationEntity r where r.id =:id"),
        @NamedQuery(name = "reservation.findByOwner", query = "SELECT r FROM ReservationEntity r where r.renterEntity.username= :owner"),
        @NamedQuery(name = "reservation.deleteById", query = "DELETE FROM ReservationEntity r where r.id =:id")
})



public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Basic
    @Column(name = "checkInDate", nullable = false)
    private Date checkInDate;

    @Basic
    @Column(name = "checkOutDate", nullable = false)
    private Date checkOutDate;

    @Basic
    @Column(name = "number_of_guests", nullable = false)
    private Integer guests;

    @Basic
    @Column(name = "approved", nullable = false)
    private Boolean approved;

    @Basic
    @Column(name = "total_cost", nullable = false)
    private double total_cost;

    @Basic
    @Column(name = "created", nullable = false)
    private Timestamp created;


       /*                                                                           */
      /*                  Σχέση  Πολλά-Προς-Ένα ((Reservation))-((Room))           */
     /*                                                                           */
    @OneToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private RoomEntity roomEntity;

    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }

       /*                                                                           */
      /*                  Σχέση  Πολλά-Προς-Ένα ((Reservation))-((Renter))         */
     /*                                                                           */
    @OneToOne
    @JoinColumn(name = "renter_username", referencedColumnName = "username")
    private RenterEntity renterEntity;

    public RenterEntity getRenterEntity() {
        return renterEntity;
    }

    public void setRenterEntity(RenterEntity renterEntity) {
        this.renterEntity = renterEntity;
    }

    public ReservationEntity() {
    }

    public ReservationEntity(Date checkInDate, Date checkOutDate, Integer guests, Boolean approved, double total_cost,
                             RoomEntity roomEntity, RenterEntity renterEntity) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guests = guests;
        this.approved = approved;
        this.total_cost = total_cost;
        this.created = new Timestamp(System.currentTimeMillis());
        this.roomEntity = roomEntity;
        this.renterEntity = renterEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Integer getGuests() {
        return guests;
    }

    public void setGuests(Integer guests) {
        this.guests = guests;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public double getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(double total_cost) {
        this.total_cost = total_cost;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Reservation toModel() {
        return new Reservation(this.id, this.roomEntity.getId(), this.renterEntity.getUsername(),
                this.checkInDate, this.checkOutDate, this.guests, this.approved, this.created, this.total_cost);
    }

}
