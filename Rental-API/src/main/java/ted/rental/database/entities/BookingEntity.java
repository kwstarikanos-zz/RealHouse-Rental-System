package ted.rental.database.entities;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class BookingEntity {

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
    @Column(name = "created", nullable = false)
    private Date created;


       /*                                                                           */
      /*                  Σχέση  Πολλά-Προς-Ένα ((Booking))-((Reservation))        */
     /*                                                                           */
    @OneToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    private ReservationEntity reservationEntity;

    public ReservationEntity getReservationEntity() {
        return reservationEntity;
    }

    public void setReservationEntity(ReservationEntity reservationEntity) {
        this.reservationEntity = reservationEntity;
    }

    public BookingEntity() {
    }

    public BookingEntity(Date checkInDate, Date checkOutDate,
                         ReservationEntity reservationEntity) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.created = new Timestamp(System.currentTimeMillis());;
        this.reservationEntity = reservationEntity;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
