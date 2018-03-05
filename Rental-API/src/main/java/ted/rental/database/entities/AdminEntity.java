package ted.rental.database.entities;

import ted.rental.annotations.Role;
import ted.rental.model.Admin;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import java.sql.Timestamp;

@Entity
@DiscriminatorValue("admin")
@PrimaryKeyJoinColumn(referencedColumnName="username")
public class AdminEntity extends UserEntity {

    public AdminEntity(){
        super();
    }

    public AdminEntity(String username, String password, String email, String role, Timestamp created) {
        super(username, password, email, role, created);
    }

    public String getRole(){
        return Role.admin.toString();
    }

    public Admin toModel(Integer depth){
        return new Admin(this.getUsername(), this.getPassword(), this.getEmail(), Role.admin, this.getCreated());
    }

    @Override
    public String toString() {
        return "AdminEntity{} " + super.toString();
    }
}
