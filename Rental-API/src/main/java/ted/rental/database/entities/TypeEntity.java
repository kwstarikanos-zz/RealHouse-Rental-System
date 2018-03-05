package ted.rental.database.entities;

import ted.rental.model.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class TypeEntity implements Serializable{

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Basic
    @Column(name = "type_name", nullable = false, length = 45)
    private String type;
    
    public TypeEntity(){

    }


    public TypeEntity(Integer id, String type) {
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

    public Type toModel(){
        return new Type(this.id, this.type);
    }
}
