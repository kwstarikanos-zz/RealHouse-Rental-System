package ted.rental.database.entities;

import ted.rental.model.Rule;

import javax.persistence.*;

@Entity
public class RuleEntity {

    @Id
    @Column(name = "id", nullable =false, unique = true)
    private Integer id;

    @Basic
    @Column(name = "rule", nullable =false , unique = true, length = 50)
    private String rule;



       /*                                                                           */
      /*     Σχέση  Πολλά-Προς-Ένα ((RuleEntity))-((ROOMENTITY_RULEENTITY))        */
     /*                                                                           */
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "ruleEntity")
    private ROOMENTITY_RULEENTITY roomentity_ruleentity;

    public ROOMENTITY_RULEENTITY getRoomentity_ruleentity() {
        return roomentity_ruleentity;
    }

    public void setRoomentity_ruleentity(ROOMENTITY_RULEENTITY roomentity_ruleentity) {
        this.roomentity_ruleentity = roomentity_ruleentity;
    }

    public RuleEntity() {
    }

    public RuleEntity(Integer id, String rule) {
        this.id = id;
        this.rule = rule;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String name) {
        this.rule = rule;
    }


    public Rule toModel(){
        return new Rule(this.id, this.rule);
    }
}
