package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Role;
import entities.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class FavouriteFacadeTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

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

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            User user = new User("user", "user");
            user.addRole(userRole);
            User admin = new User("admin", "admin");
            admin.addRole(adminRole);
            User both = new User("user_admin", "user_admin");
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @BeforeEach
    public void setup() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            User user = new User("user", "user");
            user.addRole(userRole);
            User admin = new User("admin", "admin");
            admin.addRole(adminRole);
            User both = new User("user_admin", "user_admin");
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterAll
    public static void closeTestServer() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @AfterEach
    public void afterEach() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.createQuery("DELETE FROM Favourite").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
    }

    @Test
    public void testSaveFavouriteTwitchSuccess() {
        login("user", "user");

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("channelId", "27686136");
        jsonAsMap.put("service", "twitch");

        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .body(jsonAsMap)
                .when()
                .post("/user").then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("[0].channelId", equalTo("27686136"));
    }

    @Test
    public void testSaveFavouriteYoutubeSuccess() {
        login("user", "user");

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("channelId", "UC-lHJZR3Gqxm24_Vd_AJ5Yw");
        jsonAsMap.put("service", "youtube");

        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .body(jsonAsMap)
                .when()
                .post("/user").then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("[0].channelId", equalTo("UC-lHJZR3Gqxm24_Vd_AJ5Yw"));
    }


    @Test
    public void testSaveFavouriteInvalidService() {
        login("user", "user");

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("channelId", "UC-lHJZR3Gqxm24_Vd_AJ5Yw");
        jsonAsMap.put("service", "invalidHERE");


        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .body(jsonAsMap)
                .when()
                .post("/user").then()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode())
                .body("message", equalTo("invalidHERE is not a valid Service"));
    }

    @Test
    public void testSaveFavouriteEmptyChannelName() {
        login("user", "user");

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("channelId", "");
        jsonAsMap.put("service", "youtube");


        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .body(jsonAsMap)
                .when()
                .post("/user").then()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode())
                .body("message", equalTo("Invalid input. Please try again!"));
    }


    @Test
    public void testSaveFavouriteEmptyServiceName() {
        login("user", "user");

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("channelId", "1234");
        jsonAsMap.put("service", "");


        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .body(jsonAsMap)
                .when()
                .post("/user").then()
                .statusCode(HttpStatus.BAD_REQUEST_400.getStatusCode())
                .body("message", equalTo("Invalid input. Please try again!"));
    }

    @Test
    public void testSaveFavouriteTwitchNoneExistingChannelId() {
        login("user", "user");

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("channelId", "999999999");
        jsonAsMap.put("service", "twitch");


        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .body(jsonAsMap)
                .when()
                .post("/user").then()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode())
                .body("message", equalTo("No results found for search term '999999999'"));
    }

    @Test
    public void testSaveFavouriteYoutubeNoneExistingChannelId() {
        login("user", "user");

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("channelId", "asdasd");
        jsonAsMap.put("service", "youtube");


        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .body(jsonAsMap)
                .when()
                .post("/user").then()
                .statusCode(HttpStatus.NOT_FOUND_404.getStatusCode())
                .body("message", equalTo("No content found by id 'asdasd'"));
    }
}
