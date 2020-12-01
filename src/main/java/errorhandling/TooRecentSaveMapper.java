package errorhandling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class TooRecentSaveMapper implements ExceptionMapper<TooRecentSaveException> {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Response toResponse(TooRecentSaveException exception) {
        Logger.getLogger(NoResultMapper.class.getName())
                .log(Level.FINE, null, exception);
        ExceptionDTO err = new ExceptionDTO(409,exception.getMessage());
        return Response
                .status(409)
                .entity(gson.toJson(err))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
