package errorhandling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author lam@cphbusiness.dk
 */
@Provider
public class NotFoundException implements ExceptionMapper<NotFound>{

   static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    @Override
    public Response toResponse(NotFound exception) {
        Logger.getLogger(NoResultMapper.class.getName())
                .log(Level.FINE, null, exception);
        ExceptionDTO err = new ExceptionDTO(404,exception.getMessage());
        return Response
                .status(404)
                .entity(gson.toJson(err))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
