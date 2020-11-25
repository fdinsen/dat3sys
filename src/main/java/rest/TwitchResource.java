package rest;

        import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;
        import errorhandling.NoResult;
        import facades.TwitchFacade;
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
@Path("twitch")
public class TwitchResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final TwitchFacade FACADE =  TwitchFacade.getTwitchFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Path("search/{query}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchYouTube(@PathParam("query") String query) throws NoResult {
        return Response.ok().entity(GSON.toJson(FACADE.searchTwitch(query))).build();
    }
}
