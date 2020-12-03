package facades;

import dto.SearchResultsDTO;
import dto.TwitchAnalyticsDTO;
import dto.TwitchChannelDTO;
import dto.YouTubeAnalyticsDTO;
import entities.TwitchAnalytics;
import errorhandling.NoResult;
import errorhandling.NotFound;
import errorhandling.TooRecentSaveException;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;

import java.util.List;
import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import utils.SetupTestAnalytics;

public class TwitchFacadeTest {
    private static EntityManagerFactory emf;
    private static TwitchFacade facade;

    public TwitchFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = TwitchFacade.getTwitchFacade(emf);
                SetupTestAnalytics.setUpTwitchAnalytics(emf);
    }
    
    @AfterAll
    public static void tearDownClass() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM TwitchAnalytics");
        em.getTransaction().commit();
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    @Test
    public void testSearchTwitchOnID() throws NoResult {
        String expectedID = "27686136";

        SearchResultsDTO returned = facade.searchTwitch("SivHD");
        String actualID = returned.getAll().get(0).getId();

        assertEquals(expectedID, actualID);
    }

    @Test
    public void testSearchTwitchOnName() throws NoResult {
        String expectedName = "SivHD";

        SearchResultsDTO returned = facade.searchTwitch("SivHD");
        String actualName = returned.getAll().get(0).getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    public void testSearchTwitchEmptyString() throws NoResult {
        NoResult assertThrows;

        assertThrows = Assertions.assertThrows(NoResult.class, () -> {
            SearchResultsDTO returned = facade.searchTwitch("");
        });
        Assertions.assertNotNull(assertThrows);
    }

    @Test
    public void testSearchTwitchNullString() throws NoResult {
        NoResult assertThrows;

        assertThrows = Assertions.assertThrows(NoResult.class, () -> {
            SearchResultsDTO returned = facade.searchTwitch(null);
        });
        Assertions.assertNotNull(assertThrows);
    }

    @Test
    public void testSearchTwitchOnPfpURL() throws NoResult {
        String expectedUrl = "https://static-cdn.jtvnw.net/jtv_user_pictures/a0732bbd-393f-4a16-bbe0-ac10a16b69df-profile_image-300x300.png";

        SearchResultsDTO returned = facade.searchTwitch("SivHD");
        String actualUrl = returned.getAll().get(0).getProfilePicUrl();

        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    public void testSearchTwitchOnNoResults() throws NoResult {
        NoResult assertThrows;

        assertThrows = Assertions.assertThrows(NoResult.class, () -> {
            SearchResultsDTO returned = facade.searchTwitch("aofhouasougaoughaåofascuuasd");
        });
        Assertions.assertNotNull(assertThrows);
    }


    @Test
    public void testGetTwitchChannelOnId() throws NoResult {
        String expectedID = "27686136";

        TwitchChannelDTO returned = facade.getTwitchChannel(expectedID);

        String actualID = returned.getId();

        assertEquals(expectedID, actualID);
    }


    @Test
    public void testGetTwitchChannelOnTitle() throws NoResult {
        String expectedName = "SivHD";

        TwitchChannelDTO returned = facade.getTwitchChannel("27686136");

        String actualName = returned.getTitle();

        assertEquals(expectedName, actualName);
    }


    @Test
    public void testGetTwitchChannelOnEmptyString() throws NoResult {
        NoResult assertThrows;

        assertThrows = Assertions.assertThrows(NoResult.class, () -> {
            TwitchChannelDTO returned = facade.getTwitchChannel("");
        });
        Assertions.assertNotNull(assertThrows);
    }

    @Test
    public void testGetTwitchChannelOnNullString() throws NoResult {
        NoResult assertThrows;

        assertThrows = Assertions.assertThrows(NoResult.class, () -> {
            TwitchChannelDTO returned = facade.getTwitchChannel(null);
        });
        Assertions.assertNotNull(assertThrows);
    }


    @Test
    public void testGetTwitchChannelOnPfpURL() throws NoResult {
        String expectedUrl = "https://static-cdn.jtvnw.net/jtv_user_pictures/a0732bbd-393f-4a16-bbe0-ac10a16b69df-profile_image-300x300.png";

        TwitchChannelDTO returned = facade.getTwitchChannel("27686136");
        String actualUrl = returned.getProfilePicUrl();

        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    public void testGetTwitchChannelOnNoResults() throws NoResult {
        NoResult assertThrows;

        assertThrows = Assertions.assertThrows(NoResult.class, () -> {
            TwitchChannelDTO returned = facade.getTwitchChannel("999999999");
        });
        Assertions.assertNotNull(assertThrows);
    }

    @Test
    public void testSaveTwitchAnalyticsOnId() throws NoResult, TooRecentSaveException {
        String expectedID = "27686136";

        List<TwitchAnalytics> returned = facade.saveTwitchAnalytics(expectedID);

        String actualID = returned.get(0).getChannelName();

        assertEquals(expectedID, actualID);
    }

    @Test
    public void testSaveTwitchAnalyticsOnNoResults() throws NoResult {
        NoResult assertThrows;

        assertThrows = Assertions.assertThrows(NoResult.class, () -> {
            facade.saveTwitchAnalytics("999999999");
        });
        Assertions.assertNotNull(assertThrows);
    }

    @Test
    public void testSaveTwitchAnalyticsOnNullString() throws NoResult {
        NoResult assertThrows;

        assertThrows = Assertions.assertThrows(NoResult.class, () -> {
            facade.saveTwitchAnalytics(null);
        });
        Assertions.assertNotNull(assertThrows);
    }

    @Test
    public void testSaveTwitchAnalyticsOnToRecentSave() throws TooRecentSaveException, NoResult {
        TooRecentSaveException assertThrows;

        assertThrows = Assertions.assertThrows(TooRecentSaveException.class, () -> {
            facade.saveTwitchAnalytics("27686136");
            facade.saveTwitchAnalytics("27686136");
        });
        Assertions.assertNotNull(assertThrows);
    }
    
     @Test
    public void testViewTwitchAnalyticsOnSize() throws NotFound {
        List<TwitchAnalyticsDTO> list = facade.getTwitchAnalytics("sivHD");
        int expectedSize = 3;
        int actualSize = list.size();
        
        
        assertEquals(expectedSize, actualSize);
    }
    
    @Test
    public void testViewTwitchAnalyticsNotFound() {
        NotFound assertThrows;

        assertThrows = Assertions.assertThrows(NotFound.class, () -> {
            facade.getTwitchAnalytics("test");
        });
        Assertions.assertNotNull(assertThrows);
    }
    
    @Test
    public void testViewTwitchAnalyticsNull() {
        NotFound assertThrows;

        assertThrows = Assertions.assertThrows(NotFound.class, () -> {
            facade.getTwitchAnalytics(null);
        });
        Assertions.assertNotNull(assertThrows);
    }
}
