package ted.rental.services;

import org.springframework.stereotype.Service;
import ted.rental.database.DataAccessObject;
import ted.rental.database.entities.*;
import ted.rental.exceptions.ErrorException;
import ted.rental.model.File;
import ted.rental.model.Picture;
import ted.rental.model.Profile;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class ProfileService {

    private /*static*/ DataAccessObject dao;
    private List<Profile> profiles;

    public ProfileService() {
        profiles = new ArrayList<>();
        dao = new DataAccessObject();
    }

    public List<Profile> getAllProfiles() {
        List<ProfileEntity> result = dao.getTuples("profiles.findAll");
        for (ProfileEntity profilesEntity : result) {
            profiles.add(profilesEntity.toModel());
        }
        return profiles;
    }

    public List<Profile> getAllProfilesPaginated(int start, int size) {
        dao.setStart(start);
        dao.setLimit(size);
        List<ProfileEntity> result = dao.getTuples("profiles.findAll");
        for (ProfileEntity profilesEntity : result) {
            profiles.add(profilesEntity.toModel());
        }
        return profiles;
    }

    public Profile addProfile(Profile profile) {
        ProfileEntity profilesEntity = null;
        try {
            profilesEntity = dao.insertTuple(profile.toEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (profilesEntity != null) {
            System.out.println(profilesEntity.toString());
            return profilesEntity.toModel();
        } else {
            return null;
        }
    }

    public Profile getProfile(String owner) {
        dao.setParam("owner", owner);
        List<ProfileEntity> profileEntities = dao.getTuples("profiles.findByOwner");
        dao.setParam("owner", owner);
        dao.setStart(0);
        dao.setLimit(10);
        if(!profileEntities.isEmpty()) {
            List<CommentEntity> commentEntities = dao.getTuples("comments.findByIdOrderByDate");
            profileEntities.get(0).setCommentEntities(commentEntities);
        }

        return profileEntities.isEmpty() ? null : profileEntities.get(0).toModel();

    }

    public File getProfilePicture(String username) {
        dao.setParam("owner", username);
        List<ProfileEntity> profileEntities = dao.getTuples("profiles.findByOwner");
        if(profileEntities.isEmpty())
            throw new ErrorException(Response.Status.NOT_FOUND, "The user '" + username + "' does not exist!");
        else{
            if (profileEntities.get(0).getPictureEntity() == null)
                throw new ErrorException(Response.Status.NOT_FOUND, "The user '"+ username + "' does not have a picture!");
            return profileEntities.get(0).getPictureEntity().getPicture().toModel();
        }
    }

    public String retrievePictureOwner(Integer picture_id) {
        dao.setParam("id", picture_id);
        List<ProfileEntity> profileEntities = dao.getTuples("profiles.findProfileByPictureId");
        if(profileEntities.isEmpty())
            throw new ErrorException(Response.Status.NOT_FOUND, "This user has no profile picture");
        return profileEntities.get(0).getOwner();
    }

    public void updateProfile(String username, Profile profile) {
        dao.setParam("username", username);
        List<RenterEntity> renterEntities = dao.getTuples("renter.findByUsername");
        renterEntities.get(0).setProfileEntity(profile.toEntity());
        dao.updateTuple(renterEntities.get(0));
    }


    public void updateProfilePicture(String username, File file){
        dao.setParam("username", username);
        List<RenterEntity> renterEntities = dao.getTuples("renter.findByUsername");
        FileEntity fileEntity = dao.insertTuple(file.toEntity());
        /*TODO h eikona poy 8a mpainei gia profile 8a einai medium?*/
        PictureEntity pictureEntity = new PictureEntity(null, null, fileEntity);
        renterEntities.get(0).getProfileEntity().setPictureEntity(pictureEntity);
        dao.updateTuple(renterEntities.get(0));
    }

    public void deleteProfilePicture(String username, Integer picture_id) {
        dao.setParam("owner", username);
        List<ProfileEntity> profileEntities = dao.getTuples("profiles.findByOwner");
        profileEntities.get(0).setPictureEntity(null);
        dao.updateTuple(profileEntities.get(0));
        dao.setParam("id", picture_id);
        dao.removeTuple("pictures.deleteById");
    }

    public Boolean removeProfile(String username) {
        dao.setParam("owner", username);
        return dao.removeTuple("profiles.deleteByOwner");
    }



}
