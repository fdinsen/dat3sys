package facades;

import dto.SearchResultsDTO;
import dto.YouTubeAnalyticsDTO;
import dto.YoutubeResultDTO;
import entities.TwitchAnalytics;
import entities.YouTubeAnalytics;
import errorhandling.TooRecentSaveException;
import utils.EMF_Creator;
import errorhandling.NoResult;
import errorhandling.NotFound;
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

import java.util.List;
import utils.SetupTestAnalytics;

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
        SetupTestAnalytics.setUpYTAnalytics(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM YouTubeAnalytics");
        em.getTransaction().commit();
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }


    @Test
    public void testGetChannelTitle() throws NotFound {
        String exspectedTitle = "PewDiePie";

        String pewDiePieID = "UC-lHJZR3Gqxm24_Vd_AJ5Yw";

        YoutubeResultDTO dto = facade.getChannelById(pewDiePieID);

        String actual = dto.getTitle();

        assertEquals(exspectedTitle, actual);
    }

    @Test
    public void testGetChannelDesc() throws NotFound {
        String exspecteDesc = "I make videos.";

        String pewDiePieID = "UC-lHJZR3Gqxm24_Vd_AJ5Yw";

        YoutubeResultDTO dto = facade.getChannelById(pewDiePieID);

        String actual = dto.getDesc();

        assertEquals(exspecteDesc, actual);
    }

    @Test
    public void testGetChannelViews() throws NoResult, NotFound {
        long exspectedViewCount = 26480234509L;

        String pewDiePieID = "UC-lHJZR3Gqxm24_Vd_AJ5Yw";

        YoutubeResultDTO dto = facade.getChannelById(pewDiePieID);

        long actual = dto.getViews();

        assertEquals(exspectedViewCount, actual);
    }

    @Test
    public void testGetChannelNoResult() throws NoResult {
        NotFound assertThrows;

        String noId = "";

        assertThrows = Assertions.assertThrows(NotFound.class, () -> {
            YoutubeResultDTO dto = facade.getChannelById(noId);
        });
        Assertions.assertNotNull(assertThrows);
    }

    @Test
    public void testGetChannelNullId() throws NoResult {
        NotFound assertThrows;

        assertThrows = Assertions.assertThrows(NotFound.class, () -> {
            YoutubeResultDTO dto = facade.getChannelById(null);
        });
        Assertions.assertNotNull(assertThrows);

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
        String expectedUrl = "https://yt3.ggpht.com/ytc/AAUvwng76cTETu1glc_8o4UBUiFL2v-m3818ACnK0JLFPA=s800-c-k-c0xffffffff-no-rj-mo";

        SearchResultsDTO returned = facade.searchYouTube("pewdiepie");
        String actualUrl = returned.getAll().get(0).getProfilePicUrl();

        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    public void testSearchYouTubeOnNoResults() throws NoResult {
        NoResult assertThrows;

        assertThrows = Assertions.assertThrows(NoResult.class, () -> {
            SearchResultsDTO returned = facade.searchYouTube("aofhouas");
        });
        Assertions.assertNotNull(assertThrows);
    }

    @Test
    public void testSaveYoutubeAnalyticsOnId() throws NoResult, TooRecentSaveException, NotFound {
        String expectedID = "UC-lHJZR3Gqxm24_Vd_AJ5Yw";

        List<YouTubeAnalytics> returned = facade.saveYoutubeAnalytics(expectedID);

        String actualID = returned.get(0).getChannelId();

        assertEquals(expectedID, actualID);
    }

    @Test
    public void testSaveYoutubeAnalyticsOnNoResults() throws NotFound {
        NotFound assertThrows;

        assertThrows = Assertions.assertThrows(NotFound.class, () -> {
            facade.saveYoutubeAnalytics("999999999");
        });
        Assertions.assertNotNull(assertThrows);
    }

    @Test
    public void testSaveYoutubeAnalyticsOnNullString() throws NotFound {
        NotFound assertThrows;

        assertThrows = Assertions.assertThrows(NotFound.class, () -> {
            facade.saveYoutubeAnalytics(null);
        });
        Assertions.assertNotNull(assertThrows);
    }

    @Test
    public void testSaveYoutubeAnalyticsOnToRecentSave() throws TooRecentSaveException, NoResult {
        TooRecentSaveException assertThrows;

        assertThrows = Assertions.assertThrows(TooRecentSaveException.class, () -> {
            facade.saveYoutubeAnalytics("UC-lHJZR3Gqxm24_Vd_AJ5Yw");
            facade.saveYoutubeAnalytics("UC-lHJZR3Gqxm24_Vd_AJ5Yw");
        });
        Assertions.assertNotNull(assertThrows);
    }
    
    @Test
    public void testViewYouTubeAnalyticsOnSize() throws NotFound {
        List<YouTubeAnalyticsDTO> list = facade.getYouTubeAnalytics("UC2C_jShtL725hvbm1arSV9w");
        int expectedSize = 3;
        int actualSize = list.size();
        
        
        assertEquals(expectedSize, actualSize);
    }
    
    @Test
    public void testViewYouTubeAnalyticsNotFound() {
        NotFound assertThrows;

        assertThrows = Assertions.assertThrows(NotFound.class, () -> {
            facade.getYouTubeAnalytics("test");
        });
        Assertions.assertNotNull(assertThrows);
    }
    
    @Test
    public void testViewYouTubeAnalyticsNull() {
        NotFound assertThrows;

        assertThrows = Assertions.assertThrows(NotFound.class, () -> {
            facade.getYouTubeAnalytics(null);
        });
        Assertions.assertNotNull(assertThrows);
    }

}
