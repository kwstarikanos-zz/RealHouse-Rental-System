package ted.rental.database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

import static ted.rental.config.Constraint.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "session.revoke", query = "UPDATE SessionEntity s SET s.active = false where s.username = :username"),
        @NamedQuery(name = "session.isActive", query = "select s from SessionEntity s where s.active = true and s.username = :username and s.token = :token"),
})
public class SessionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Basic
    @Column(name = "login_username", nullable = false, length = USERNAME_MAX)
    private String username;

    @Basic
    @Column(name = "token", nullable = false, length = TOKEN_MAX)
    private String token;

    @Basic
    @Column(name = "ip", nullable = false, length = 15)
    private String ip;

    @Basic
    @Column(name = "active", nullable = false)
    private Boolean active;

    @Basic
    @Column(name = "start", nullable = false)
    private Timestamp start;

    @Basic
    @Column(name = "expiration", nullable = false)
    private Timestamp expiration;

      /*                                                                         */
     /*                 Σχέση Ένα-Προς-Ένα ((Session)-((User))                  */
    /*                                                                         */
    @OneToOne
    @JoinColumn(name = "login_username", referencedColumnName = "username", insertable = false, updatable = false)
    private UserEntity userEntity;

    public SessionEntity() {

    }

    public SessionEntity(String username, String token, String ip, Timestamp start, Timestamp expiration) {
        this.username = username;
        this.token = token;
        this.ip = ip;
        this.active = true;
        this.start = start;
        this.expiration = expiration;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getExpiration() {
        return expiration;
    }

    public void setExpiration(Timestamp expiration) {
        this.expiration = expiration;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public String toString() {
        return "SessionEntity{" +
                "username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", ip='" + ip + '\'' +
                ", active=" + active +
                ", start=" + start +
                ", expiration=" + expiration +
                ", userEntity=" + userEntity +
                '}';
    }
}
