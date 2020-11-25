package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import errorhandling.NoResult;
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
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = FACADE.getRenameMeCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }
    
    @Path("search/{query}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchYouTube(@PathParam("query") String query) throws NoResult {
        return Response.ok().entity(GSON.toJson(FACADE.searchYouTube(query))).build();
    }
}
