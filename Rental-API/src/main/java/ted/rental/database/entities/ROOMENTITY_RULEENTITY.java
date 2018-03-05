package ted.rental.database.entities;

import javax.persistence.*;


@Entity
@Table(name = "ROOMENTITY_RULEENTITY")
public class ROOMENTITY_RULEENTITY {

        @EmbeddedId
        private RoomRuleId roomRuleId = new RoomRuleId();


       /*                                                                           */
      /*          Σχέση  Πολλά-Προς-Ένα ((ROOMENTITY_RULEENTITY))-((Room))         */
     /*                                                                           */
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RoomEntity roomEntity;

    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }

    /*                                                                           */
      /*        Σχέση  Πολλά-Προς-Ένα ((ROOMENTITY_RULEENTITY))-((Rule))           */
     /*                                                                           */
    @OneToOne
    @JoinColumn(name = "rule_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RuleEntity ruleEntity;

    public RuleEntity getRuleEntity() {
        return ruleEntity;
    }

    public void setRuleEntity(RuleEntity ruleEntity) {
        this.ruleEntity = ruleEntity;
    }

    public ROOMENTITY_RULEENTITY() {
    }

    public ROOMENTITY_RULEENTITY(Integer room_id, Integer rule_id) {
        this.roomRuleId.setRoom_id(room_id);
        this.roomRuleId.setRule_id(rule_id);
    }

    public RoomRuleId getRoomRuleId() {
        return roomRuleId;
    }

    public void setRoomRuleId(RoomRuleId roomRuleId) {
        this.roomRuleId = roomRuleId;
    }
}
