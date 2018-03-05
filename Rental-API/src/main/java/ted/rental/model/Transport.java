package ted.rental.model;

import ted.rental.database.entities.RuleEntity;
import ted.rental.database.entities.TransportEntity;

import javax.validation.constraints.NotNull;

public class Transport {

    @NotNull
    private Integer id;

    private String transport;

    public Transport() {
    }

    public Transport(Integer id, String transport) {
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

    @Override
    public String toString() {
        return "Transport{" +
                "id=" + id +
                ", transport='" + transport + '\'' +
                '}';
    }


    public TransportEntity toEntity() {
        return new TransportEntity(this.id, this.transport);
    }
}
