package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.FavouriteDTO;
import errorhandling.InvalidInputException;
import errorhandling.InvalidServiceException;
import errorhandling.NoResult;
import errorhandling.NotFound;
import facades.FavouriteFacade;
import security.JWTSecurityContext;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("user")
public class FavouritesResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final FavouriteFacade FACADE =  FavouriteFacade.getFavouriteFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @Context
    SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    @Path("favourite")
    public String saveFavourite(String json) throws NoResult, NotFound, AuthenticationException, InvalidInputException, InvalidServiceException {
        String username = securityContext.getUserPrincipal().getName();
        FavouriteDTO favouriteDTO = GSON.fromJson(json, FavouriteDTO.class);
        List<FavouriteDTO> favouriteDTOList = FACADE.saveFavourite(favouriteDTO, username);
        return GSON.toJson(favouriteDTOList);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    @Path("favourite")
    public String getFavourites(String json) throws NoResult, NotFound, AuthenticationException, InvalidInputException, InvalidServiceException {
        String username = securityContext.getUserPrincipal().getName();
        List<FavouriteDTO> favouriteDTOList = FACADE.getUserFavourites(username);
        return GSON.toJson(favouriteDTOList);
    }
}
