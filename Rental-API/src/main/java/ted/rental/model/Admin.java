package ted.rental.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import ted.rental.annotations.Role;
import ted.rental.database.entities.AdminEntity;

import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Admin extends User {

    /*GET Constructor*/
    public Admin(String username, String password, String email, Role role, Timestamp created) {
        super(username, password, email, role, created);
    }

    
    public Role getRole(){
        return Role.admin;
    }

    public Boolean getConfirmed() {
        return true;
    }

    public void aproveUser(String username){
        /*TODO: <----------------------------------------------------------------------------------------------------*/

    }

    public void deleteUser(String username){
        /*TODO: <----------------------------------------------------------------------------------------------------*/

    }

    @Override
    public String toString() {
        return "AdminEntity{} " + super.toString();
    }

    public AdminEntity toEntity(){
        return new AdminEntity(this.getUsername(), this.getPassword(), this.getEmail(), this.getRole().toString(), this.getCreated());
    }

}
