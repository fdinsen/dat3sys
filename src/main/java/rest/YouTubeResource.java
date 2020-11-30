package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import errorhandling.NoResult;
import errorhandling.NotFound;
import utils.EMF_Creator;
import facades.YoutubeFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("youtube")
public class YouTubeResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final YoutubeFacade FACADE =  YoutubeFacade.getYoutubeFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    
    @Path("search/{query}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchYouTube(@PathParam("query") String query) throws NoResult {
        return Response.ok().entity(GSON.toJson(FACADE.searchYouTube(query))).build();
    }
    
    @Path("channel/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChannelInfo(@PathParam("id") String id) throws NotFound{
        return Response.ok().entity(GSON.toJson(FACADE.getChannelById(id))).build();
    }
}
