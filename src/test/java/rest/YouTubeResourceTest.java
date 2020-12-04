package rest;

import entities.Role;
import entities.User;
import facades.YoutubeFacade;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.SetupTestAnalytics;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class YouTubeResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        YoutubeFacade.runThroughGrizzly();
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @AfterEach
    public void afterEach() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("delete from YouTubeAnalytics ").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testSearchYouTubeOnID() {
        given()
                .contentType("application/json")
                .get("/youtube/search/pewdiepie").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("all[0].id", equalTo("UC-lHJZR3Gqxm24_Vd_AJ5Yw"));
    }

    @Test
    public void testSearchYouTubeOnName() {
        given()
                .contentType("application/json")
                .get("/youtube/search/pewdiepie").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("all[0].name", equalTo("PewDiePie"));
    }

    @Test
    public void testSearchYouTubeOnPfpUrl() {
        given()
                .contentType("application/json")
                .get("/youtube/search/pewdiepie").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("all[0].profilePicUrl", equalTo("https://yt3.ggpht.com/ytc/AAUvwng76cTETu1glc_8o4UBUiFL2v-m3818ACnK0JLFPA=s800-c-k-c0xffffffff-no-rj-mo"));
    }

    @Test
    public void testSearchYouTubeOnEmptyString() {
        given()
                .contentType("application/json")
                .get("/youtube/search/").then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }

    @Test
    public void testSearchYouTubeOnNoResults() {
        given()
                .contentType("application/json")
                .get("/youtube/search/adhou").then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode())
                .body("message", equalTo("No results found for search term 'adhou'"));
    }

    @Test
    public void testGetChannelTitle() {
        String pewDiePieID = "UC-lHJZR3Gqxm24_Vd_AJ5Yw";

        given()
                .contentType("application/json")
                .get("/youtube/channel/" + pewDiePieID)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("title", equalTo("PewDiePie"));
    }

    @Test
    public void testGetChannelViews() {
        String pewDiePieID = "UC-lHJZR3Gqxm24_Vd_AJ5Yw";

        given()
                .contentType("application/json")
                .get("/youtube/channel/" + pewDiePieID)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("country", equalTo("US"));
    }

    @Test
    public void testGetChannelTopicCategories() {
        String pewDiePieID = "UC-lHJZR3Gqxm24_Vd_AJ5Yw";

        given()
                .contentType("application/json")
                .get("/youtube/channel/" + pewDiePieID)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("topicCategories.size()", equalTo(3));
    }

    @Test
    public void testGetChannelError() {
        String wrongId = "";

        given()
                .contentType("application/json")
                .get("/youtube/channel/1111")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode())
                .body("code", equalTo(404))
                .body("message", equalTo("No content found by id '1111'"));
    }

    @Test
    public void testViewYouTubeAnalyticsOnSize() {
        SetupTestAnalytics.setUpYTAnalytics(emf);
        String id = "UC2C_jShtL725hvbm1arSV9w";
        given()
                .contentType("application/json")
                .get("/youtube/get-analytics/" + id)
                .then().assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(3));
    }

    @Test
    public void testViewYouTubeAnalyticsNoResult() {
        String id = "hej";
        given()
                .contentType("application/json")
                .get("/youtube/get-analytics/" + id)
                .then().assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode())
                .body("code", equalTo(404))
                .body("message", equalTo("No content found by id 'hej'"));
    }

    @Test
    public void testViewYouTubeAnalyticsNull() {
        given()
                .contentType("application/json")
                .get("/youtube/get-analytics/")
                .then().assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode())
                .body("code", equalTo(404))
                .body("message", equalTo("HTTP 404 Not Found"));
    }

    @Test
    public void testSaveYoutubeChannelOnSize() {
        given()
                .contentType("application/json")
                .get("/youtube/save/UC-lHJZR3Gqxm24_Vd_AJ5Yw").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(1));
    }

    @Test
    public void testSaveYoutubeChannelOnToRecent() {
        given()
                .contentType("application/json")
                .get("/youtube/save/UC-lHJZR3Gqxm24_Vd_AJ5Yw");

        given()
                .contentType("application/json")
                .get("/youtube/save/UC-lHJZR3Gqxm24_Vd_AJ5Yw").then()
                .assertThat()
                .statusCode(HttpStatus.CONFLICT_409.getStatusCode());
    }

    @Test
    public void testSaveYoutubeChannelOnNotFound() {
        given()
                .contentType("application/json")
                .get("/youtube/save/999999999").then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }
}
