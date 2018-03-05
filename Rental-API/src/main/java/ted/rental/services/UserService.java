package ted.rental.services;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jdk.internal.org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.springframework.stereotype.Service;
import ted.rental.database.DataAccessObject;
import ted.rental.database.entities.HostEntity;
import ted.rental.database.entities.ProfileEntity;
import ted.rental.database.entities.RenterEntity;
import ted.rental.database.entities.UserEntity;
import ted.rental.model.*;
import ted.rental.model.inputs.Register;

import javax.inject.Inject;

@Service
public class UserService {

    public UserService() {

    }

    public List<User> getAllUsers(int depth) {
        DataAccessObject dao = new DataAccessObject();
        List<User> users = new ArrayList<>();
        List<UserEntity> entities = dao.getTuples("users.findAll");
        for (UserEntity entity : entities) {
            users.add(entity.toModel(depth));
        }
        return users;
    }

    public List<User> getUsersByRole(String role){
        DataAccessObject dao = new DataAccessObject();
        List<User> users = new ArrayList<>();
        dao.setParam("role", role);
        List<UserEntity> entities = dao.getTuples("users.findUsersByRole");
        System.out.println(entities.toString());
        for (UserEntity entity : entities) {
            users.add(entity.toModel(1));
        }
        return users;
    }

    public List<User> getAllUsersPaginated(int start, int size, int depth) {
        DataAccessObject dao = new DataAccessObject();
        List<User> users = new ArrayList<>();
        dao.setStart(start);
        dao.setLimit(size);
        List<UserEntity> entities = dao.getTuples("users.findAll");
        for (UserEntity entity : entities) {
            users.add(entity.toModel(depth));
        }
        return users;
    }

    public User addUser(final Register register) {
        DataAccessObject dao = new DataAccessObject();
        User newUser;

        Profile newProfile = register.getProfile();
        if (newProfile != null)
            newProfile.setOwner(register.getUsername());

        //User newUser;
        if (register.getHost())
            newUser = new Host(register.getProfile(), register.getUsername(), register.getPassword(), register.getEmail(), false);
        else
            newUser = new Renter(register.getProfile(), register.getUsername(), register.getPassword(), register.getEmail());
        newUser.setCreated(new Timestamp(System.currentTimeMillis()));

        System.out.println("I create this:");
        System.out.println(newUser.toString());


        //throw new ErrorException(CONFLICT, "Failed to add the user successfully, probably the user already exists! ERROR: " + e.getCause());
        System.out.println("Try to insert entity to database....");
        UserEntity entity = dao.insertTuple(newUser.toEntity());
        System.out.println("User inserted!");
        return (entity == null) ? null : entity.toModel(0);
    }

    public User addUser(User user) {
        DataAccessObject dao = new DataAccessObject();
        user.setCreated(new Timestamp(System.currentTimeMillis()));
        UserEntity entity = null;
        entity = dao.insertTuple(user.toEntity());
        return (entity == null) ? null : entity.toModel(0);
    }

    public User getUserByUsername(String username) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("username", username);
        List<UserEntity> entities = dao.getTuples("users.findByUsername");
        return entities.isEmpty() ? null : entities.get(0).toModel(1);
    }

    public User getUserByEmail(String email) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("email", email);
        List<UserEntity> entities = dao.getTuples("users.findByEmail");
        return entities.isEmpty() ? null : entities.get(0).toModel(1);
    }

    public User getUserByPhone(String phone) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("phone", phone);
        List<UserEntity> entities = dao.getTuples("renter.findByPhone");
        return entities.isEmpty() ? null : entities.get(0).toModel(1);
    }

    public User findByUsernameAndPassword(String username, String password) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("username", username);
        dao.setParam("password", password);
        List<UserEntity> entities = dao.getTuples("users.findByUsernameAndPassword");
        return entities.isEmpty() ? null : entities.get(0).toModel(1);
    }

    public void updateUser(User user, String username) {
        DataAccessObject dao = new DataAccessObject();
        user.setUsername(username);
        dao.updateTuple(user.toEntity());
    }


    public void confirmUserByUsername(String username){
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("username", username);
        List <HostEntity> entity = dao.getTuples("find.hostByUsername");
        if(!entity.get(0).isConfirmed()){
            entity.get(0).setConfirmed(true);
            dao.updateTuple(entity.get(0));
        }
    }

    public Boolean deleteUser(String username) {
        DataAccessObject dao = new DataAccessObject();
        dao.setParam("owner", username);
        if(dao.removeTuple("profiles.deleteByOwner")){
            dao.setParam("username", username);
            return dao.removeTuple("users.deleteByUsername");
        }else
            return false;
    }
}
