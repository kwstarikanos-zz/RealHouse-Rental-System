package ted.rental.model;


import org.hibernate.validator.constraints.Length;
import ted.rental.database.entities.RuleEntity;

import javax.validation.constraints.NotNull;

public class Rule {

    private Integer id;

    @NotNull
    @Length(max = 50)
    private String rule;

    public Rule() {
    }

    public Rule(Integer id, String rule) {
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

    public void setRule(String rule) {
        this.rule = rule;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "id=" + id +
                ", rule='" + rule + '\'' +
                '}';
    }

    public RuleEntity toEntity(){
        return new RuleEntity(this.id, this.rule);
    }
}
