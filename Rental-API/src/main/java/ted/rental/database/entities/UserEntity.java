package ted.rental.database.entities;

import ted.rental.annotations.Role;
import ted.rental.model.User;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import static ted.rental.config.Constraint.*;

@Entity
@Inheritance( strategy = InheritanceType.JOINED )
@DiscriminatorColumn(name = "role")
@NamedQueries({
        @NamedQuery(name = "users.findAll", query = "SELECT u FROM UserEntity u"),
        @NamedQuery(name = "users.findByUsernameAndPassword", query = "SELECT u FROM UserEntity u WHERE u.username = :username AND u.password = :password"),
        @NamedQuery(name = "users.findByUsername", query = "SELECT u FROM UserEntity u WHERE u.username = :username"),
        @NamedQuery(name = "users.findByEmail", query = "SELECT u FROM UserEntity u WHERE u.email = :email"),
        /*TODO: range (from-to)*/
        @NamedQuery(name = "users.findByCreated", query = "SELECT u FROM UserEntity u WHERE u.created = :created"),
        @NamedQuery(name = "users.deleteByUsername", query = "DELETE  FROM UserEntity u WHERE u.username = :username"),
        @NamedQuery(name = "users.findUsersByRole", query = "SELECT u FROM UserEntity u WHERE u.role = :role")
})
public abstract class UserEntity implements Serializable {

    @Id
    @Column(name = "username", unique = true, nullable = false, length = USERNAME_MAX)
    private String username;

    @Basic
    @Column(name ="password", nullable = false, length = USERNAME_MAX)
    private String password;

    @Basic
    @Column(name = "email", unique = true, nullable = false, length = 45)
    private String email;

    @Basic
    @Column(name = "role", nullable = false, length = 6)
    private String role;

    @Basic
    @Column(name = "created", nullable = false)
    private Timestamp created;



      /*                                                                         */
     /*                 Σχέση Ένα-Προς-Ένα ((User))-((Session)                  */
    /*                                                                         */
    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private SessionEntity sessionEntity;

    public SessionEntity getSessionEntity() {
        return sessionEntity;
    }

    public void setSessionEntity(SessionEntity sessionEntity) {
        this.sessionEntity = sessionEntity;
    }

    public UserEntity() {
        super();
    }

    public UserEntity(String username, String password, String email, String role, Timestamp created) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.created = created;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public abstract User toModel(Integer depth);

    @Override
    public String toString() {
        return "UserEntity{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", created=" + created +
                '}';
    }
}
