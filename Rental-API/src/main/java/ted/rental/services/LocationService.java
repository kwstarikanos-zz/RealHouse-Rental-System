package ted.rental.services;

import org.springframework.stereotype.Service;
import ted.rental.database.DataAccessObject;
import ted.rental.database.entities.LocationEntity;
import ted.rental.model.Location;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class LocationService {

    private List<Location> locations;

    public LocationService(){
        locations = new ArrayList<>();
    }

    public Location addLocation(Location location) {
        DataAccessObject dao = new DataAccessObject();
        LocationEntity entity = null;
        try {
            entity = dao.insertTuple(location.toEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (entity != null) {
            System.out.println(entity.toString());

            return entity.toModel();
        } else {
            return null;
        }
    }
}
