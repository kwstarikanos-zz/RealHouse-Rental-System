package ted.rental.exceptions;

import ted.rental.model.outputs.ErrorObj;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ErrorException extends WebApplicationException {

    private static final long serialVersionUID = 6817489620338221395L;

    private Object reason;

    public ErrorException(final Response.Status status, final Object reason) {
        super(Response
                .status(status)
                .entity(new ErrorObj(status.getReasonPhrase(), status.getStatusCode(), reason))
                .type(MediaType.APPLICATION_JSON)
                .build()
        );
        this.reason = reason;
    }

    @Override
    public String getMessage(){
        return reason.toString();
    }

}
