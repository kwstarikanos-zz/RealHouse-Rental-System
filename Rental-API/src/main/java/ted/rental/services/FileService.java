package ted.rental.services;

import ted.rental.database.DataAccessObject;
import ted.rental.database.entities.PictureEntity;
import ted.rental.database.entities.ROOMENTITY_PICTUREENTITY;
import ted.rental.exceptions.ErrorException;
import ted.rental.model.File;

import javax.ws.rs.core.Response;
import java.util.List;

public class FileService {

    public FileService() {

    }

    public File getImageByIdAndSize(Integer picture_id, String size) {
        DataAccessObject dao = new DataAccessObject();
        File file = null;
        dao.setParam("id", picture_id);
        List<PictureEntity> entities = dao.getTuples("pictures.findById");
        if (!entities.isEmpty()) {
            if (size != null) {
                if (size.equals("thumbnail")) {
                    file = entities.get(0).getThumbnail() == null ? null : entities.get(0).getThumbnail().toModel();
                } else if (size.equals("medium")) {
                    file = entities.get(0).getMedium() == null ? null : entities.get(0).getMedium().toModel();
                } else if (size.equals("original")) {
                    file = entities.get(0).getPicture() == null ? null : entities.get(0).getPicture().toModel();
                }
            } else
                file = entities.get(0).getPicture() == null ? null : entities.get(0).getPicture().toModel();
        } else
            throw new ErrorException(Response.Status.NOT_FOUND, "The picture does not exist!");
        if(file == null)
            throw new ErrorException(Response.Status.NOT_FOUND, "The picture does not exist at size '" + size + "'");
        return file;
    }

}
