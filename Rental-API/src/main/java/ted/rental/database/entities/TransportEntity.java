package ted.rental.database.entities;

import ted.rental.model.Amenities;

import javax.persistence.*;

@Entity
public class TransportEntity {

    @Id
    @Column(name = "id", nullable =false)
    private Integer id;

    @Basic
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String transport;


       /*                                                                           */
      /*  Σχέση  Πολλά-Προς-Ένα ((TransportEntity))-((ROOMENTITY_TRANSPORTENTITY)) */
     /*                                                                           */
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "transportEntity")
    private ROOMENTITY_TRANSPORTENTITY roomentity_transportentity;

    public ROOMENTITY_TRANSPORTENTITY getRoomentity_transportentity() {
        return roomentity_transportentity;
    }

    public void setRoomentity_transportentity(ROOMENTITY_TRANSPORTENTITY roomentity_transportentity) {
        this.roomentity_transportentity = roomentity_transportentity;
    }

    public TransportEntity() {
    }

    public TransportEntity(Integer id, String transport) {
        this.id = id;
        this.transport = transport;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }
}
