package ted.rental.database.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
public class RoomRuleId implements Serializable{

    @Column(name = "room_id", nullable = false)
    private Integer room_id;

    @Column(name = "rule_id", nullable = false)
    private Integer rule_id;

    public RoomRuleId() {
    }

    public RoomRuleId(Integer room_id, Integer rule_id) {
        this.room_id = room_id;
        this.rule_id = rule_id;
    }

    public Integer getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Integer room_id) {
        this.room_id = room_id;
    }

    public Integer getRule_id() {
        return rule_id;
    }

    public void setRule_id(Integer rule_id) {
        this.rule_id = rule_id;
    }

    @Override
    public String toString() {
        return "RoomRuleId{" +
                "room_id=" + room_id +
                ", rule_id=" + rule_id +
                '}';
    }
}
