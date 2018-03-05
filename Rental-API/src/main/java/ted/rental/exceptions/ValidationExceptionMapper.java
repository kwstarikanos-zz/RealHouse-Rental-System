package ted.rental.exceptions;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.validation.ResponseConstraintViolationException;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    private static final Logger LOG = LogUtils.getL7dLogger(org.apache.cxf.jaxrs.validation.ValidationExceptionMapper.class);

    @Override
    public Response toResponse(ValidationException exception) {

        if (exception instanceof ConstraintViolationException) {
            final ConstraintViolationException constraint = (ConstraintViolationException) exception;
            final boolean isResponseException = constraint instanceof ResponseConstraintViolationException;
            Set<String> violationMessages = new HashSet<String>();
            for (final ConstraintViolation<?> violation : constraint.getConstraintViolations()) {
                violationMessages.add(violation.getPropertyPath() + ": " + violation.getMessage());
                LOG.log(Level.WARNING,
                        violation.getRootBeanClass().getSimpleName()
                                + "." + violation.getPropertyPath()
                                + ": " + violation.getMessage());
            }
            if (isResponseException) {

                return new ErrorException(INTERNAL_SERVER_ERROR, "Oops, something went wrong! Response isn't valid.").getResponse();
            }
            return new ErrorException(BAD_REQUEST, violationMessages).getResponse();
        } else {
            return new ErrorException(INTERNAL_SERVER_ERROR, "Oops, something went wrong!").getResponse();
        }
    }
}
