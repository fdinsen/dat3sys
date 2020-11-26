package facades;

import dto.SearchResultsDTO;
import errorhandling.NoResult;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TwitchFacadeTest {
    private static EntityManagerFactory emf;
    private static TwitchFacade facade;

    public TwitchFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = TwitchFacade.getTwitchFacade(emf);
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
}
