package ted.rental.endpoints;

import org.apache.cxf.security.SecurityContext;
import ted.rental.exceptions.ErrorException;
import ted.rental.model.File;
import ted.rental.services.FileService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/files")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FileEndPoint {

    private static FileService fileService;

    @Context
    SecurityContext securityContext;

    public FileEndPoint() {
        fileService = new FileService();
    }

    @GET
    @Path("/picture/{picture_id}")
    public Response getImageByIdAndSize(@PathParam("picture_id") Integer picture_id, @QueryParam("size") String size) {
        File file = fileService.getImageByIdAndSize(picture_id, size);
        return Response.status(Response.Status.OK).entity(file).build();
    }
}
