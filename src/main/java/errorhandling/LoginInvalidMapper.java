/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author gamma
 */
@Provider
public class LoginInvalidMapper implements ExceptionMapper<LoginInvalid>{
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    @Override
    public Response toResponse(LoginInvalid exception) {
        Logger.getLogger(LoginInvalidMapper.class.getName())
                .log(Level.FINE, null, exception);
        ExceptionDTO err = new ExceptionDTO(403,exception.getMessage());
        return Response
                .status(403)
                .entity(gson.toJson(err))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    
}
