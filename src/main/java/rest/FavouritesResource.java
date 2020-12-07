package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.FavouriteDTO;
import errorhandling.InvalidInputException;
import errorhandling.InvalidServiceException;
import errorhandling.NoResult;
import errorhandling.NotFound;
import facades.FavouriteFacade;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("user")
public class FavouritesResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final FavouriteFacade FACADE =  FavouriteFacade.getFavouriteFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String searchYouTube(String json) throws NoResult, NotFound, AuthenticationException, InvalidInputException, InvalidServiceException {
        FavouriteDTO favouriteDTO = GSON.fromJson(json, FavouriteDTO.class);
        List<FavouriteDTO> favouriteDTOList = FACADE.saveFavourite(favouriteDTO, "user");
        return GSON.toJson(favouriteDTOList);
    }
}
