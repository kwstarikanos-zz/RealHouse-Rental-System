package ted.rental.model;

import org.hibernate.validator.constraints.Length;
import ted.rental.database.entities.TypeEntity;

import javax.validation.constraints.NotNull;

public class Type {

    private Integer id;

    @NotNull
    @Length(max = 45)
    private String type;

    public Type(){}

    public Type(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }

    public TypeEntity toEntity(){
        return new TypeEntity(this.id , this.type);
    }
}
