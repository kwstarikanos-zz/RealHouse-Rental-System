package ted.rental.services;

import org.springframework.stereotype.Service;
import ted.rental.database.DataAccessObject;
import ted.rental.database.entities.SessionEntity;

import java.sql.Timestamp;
import java.util.List;

@Service
public class SessionService {

    public SessionService() {

    }

    public void create(String username, String token, String ip, Timestamp start, Timestamp expiration) {
        DataAccessObject dao = new DataAccessObject();
        dao.insertTuple(new SessionEntity(username, token, ip, start, expiration));
    }

    public Integer revoke(String username) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("username", username);
        return dao.updateTyplesWithNamedQuery("session.revoke");
    }

    public Boolean isActive(String username, String token) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("username", username);
        dao.setParam("token", token);
        List<SessionEntity> entities = dao.getTuples("session.isActive");
        return !entities.isEmpty();
    }
}
