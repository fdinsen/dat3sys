package rest;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import utils.SetupTestAnalytics;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class TwitchResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
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

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
    }

    @Test
    public void testSearchTwitchOnID() {
        given()
                .contentType("application/json")
                .get("/twitch/search/SivHD").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("all[0].id", equalTo("27686136"));
    }

    @Test
    public void testSearchTwitchOnName() {
        given()
                .contentType("application/json")
                .get("/twitch/search/SivHD").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("all[0].name", equalTo("SivHD"));
    }

    @Test
    public void testSearchTwitchOnPfpUrl() {
        given()
                .contentType("application/json")
                .get("/twitch/search/SivHD").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("all[0].profilePicUrl", equalTo("https://static-cdn.jtvnw.net/jtv_user_pictures/a0732bbd-393f-4a16-bbe0-ac10a16b69df-profile_image-300x300.png"));
    }

    @Test
    public void testSearchTwitchOnEmptyString() {
        given()
                .contentType("application/json")
                .get("/twitch/search/").then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode());
    }

    @Test
    public void testSearchTwitchOnNoResults() {
        given()
                .contentType("application/json")
                .get("/twitch/search/adhouahgbibeqibf8i2n2").then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode())
                .body("message", equalTo("No results found for search term 'adhouahgbibeqibf8i2n2'"));
    }

    @Test
    public void testGetTwitchChannelOnID() {
        given()
                .contentType("application/json")
                .get("/twitch/channel/27686136").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("id", equalTo("27686136"));
    }

    @Test
    public void testGetTwitchChannelOnName() {
        given()
                .contentType("application/json")
                .get("/twitch/channel/27686136").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("title", equalTo("SivHD"));
    }

    @Test
    public void testGetTwitchChannelOnPfpUrl() {
        given()
                .contentType("application/json")
                .get("/twitch/channel/27686136").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("profilePicUrl", equalTo("https://static-cdn.jtvnw.net/jtv_user_pictures/a0732bbd-393f-4a16-bbe0-ac10a16b69df-profile_image-300x300.png"));
    }

    @Test
    public void testGetTwitchChannelOnGame() {
        given()
                .contentType("application/json")
                .get("/twitch/channel/27686136").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("game", equalTo("League of Legends"));
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
    public void testViewTwitchAnalyticsOnSize() {
        String id = "SivHD";
        given()
                .contentType("application/json")
                .get("/twitch/get-analytics/" + id)
                .then().assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(3));
    }

    @Test
    public void testViewTwitchAnalyticsNoResult() {
        String id = "hej";
        given()
                .contentType("application/json")
                .get("/twitch/get-analytics/" + id)
                .then().assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode())
                .body("code", equalTo(404))
                .body("message", equalTo("No content found by id 'hej'"));
    }

    @Test
    public void testViewTwitchAnalyticsNull() {
        given()
                .contentType("application/json")
                .get("/twitch/get-analytics/")
                .then().assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode())
                .body("code", equalTo(404))
                .body("message", equalTo("HTTP 404 Not Found"));
    }
}
