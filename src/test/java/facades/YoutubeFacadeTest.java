package facades;

import dto.SearchResultsDTO;
import dto.internaldto.YTSearchResultDTO;
import utils.EMF_Creator;
import entities.RenameMe;
import errorhandling.NoResult;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class YoutubeFacadeTest {

    private static EntityManagerFactory emf;
    private static YoutubeFacade facade;

    public YoutubeFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = YoutubeFacade.getYoutubeFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("RenameMe.deleteAllRows").executeUpdate();
            em.persist(new RenameMe("Some txt", "More text"));
            em.persist(new RenameMe("aaa", "bbb"));

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testAFacadeMethod() {
        assertEquals(2, facade.getRenameMeCount(), "Expects two rows in the database");
    }
    
    @Test
    public void testSearchYouTubeOnID() throws NoResult {
        String expectedID = "UC-lHJZR3Gqxm24_Vd_AJ5Yw";
        
        SearchResultsDTO returned = facade.searchYouTube("pewdiepie");
        String actualID = returned.getAll().get(0).getId();
                
        assertEquals(expectedID, actualID);
    }
    
    @Test
    public void testSearchYouTubeOnName() throws NoResult {
        String expectedName = "PewDiePie";
        
        SearchResultsDTO returned = facade.searchYouTube("pewdiepie"); 
        String actualName = returned.getAll().get(0).getName();
                
        assertEquals(expectedName, actualName);
    }
    
    @Test
    public void testSearchYouTubeEmptyString() throws NoResult {
        NoResult assertThrows;
        
        assertThrows = Assertions.assertThrows(NoResult.class, () -> {
            SearchResultsDTO returned = facade.searchYouTube("");
        });
        Assertions.assertNotNull(assertThrows);
    }
    
    @Test
    public void testSearchYouTubeNullString() throws NoResult {
        NoResult assertThrows;
        
        assertThrows = Assertions.assertThrows(NoResult.class, () -> {
            SearchResultsDTO returned = facade.searchYouTube(null);
        });
        Assertions.assertNotNull(assertThrows);
    }
    
    @Test
    public void testSearchYouTubeOnPfpURL() throws NoResult {
        String expectedUrl = "https://yt3.ggpht.com/ytc/AAUvwnjx8z2LzkTZERwlyjBSkWBZqEv0w07wsXZDY06u=s800-c-k-c0xffffffff-no-rj-mo";
        
        SearchResultsDTO returned = facade.searchYouTube("gammarik");
        String actualUrl = returned.getAll().get(0).getProfilePicUrl();
        
        assertEquals(expectedUrl, actualUrl);
    }
    
    @Test
    public void testSearchYouTubeOnNoResults() throws NoResult {
        NoResult assertThrows;
        
        assertThrows = Assertions.assertThrows(NoResult.class, () -> {
            SearchResultsDTO returned = facade.searchYouTube("aofhouasougaoughaåofascuuasd");
        });
        Assertions.assertNotNull(assertThrows);
    }

}
