package facades;

import dto.FavouriteDTO;
import dto.SearchResultsDTO;
import entities.Favourite;
import entities.TwitchAnalytics;
import entities.User;
import errorhandling.*;
import org.junit.jupiter.api.*;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;
import utils.SetupTestAnalytics;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FavouriteFacadeTest {
    private static EntityManagerFactory emf;
    private static FavouriteFacade facade;
    private User user1;
    private User user2;
    public FavouriteFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = FavouriteFacade.getFavouriteFacade(emf);
        SetupTestAnalytics.setUpTwitchAnalytics(emf);
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        user1 = new User("user1", "user1");
        user2 = new User("user2", "user2");

        Favourite favourite1 = new Favourite("27686136", "twitch");
        Favourite favourite2 = new Favourite("UC-lHJZR3Gqxm24_Vd_AJ5Yw", "youtube");

        List<Favourite> favouritesList = new ArrayList<>();
        favouritesList.add(favourite1);
        favouritesList.add(favourite2);
        user1.setFavouriteList(favouritesList);

        try {
            em.getTransaction().begin();
            em.persist(user1);
            em.persist(user2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void reset() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM User").executeUpdate();
        em.createQuery("DELETE FROM Favourite").executeUpdate();
        em.getTransaction().commit();
    }

    @AfterAll
    public static void tearDownClass() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Favourite");
        em.getTransaction().commit();
    }

    @Test
    public void testSaveFavouriteYoutubeSuccess() throws InvalidServiceException, AuthenticationException, InvalidInputException, NoResult, NotFound {
        FavouriteDTO favouriteDTO = new FavouriteDTO("UC-lHJZR3Gqxm24_Vd_AJ5Yw", "youtube");

        List<FavouriteDTO> returned = facade.saveFavourite(favouriteDTO, user1.getUserName());

        String actualID = returned.get(1).getChannelId();

        assertEquals(favouriteDTO.getChannelId(), actualID);
    }

    @Test
    public void testSaveFavouriteTwitchSuccess() throws InvalidServiceException, AuthenticationException, InvalidInputException, NoResult, NotFound {
        FavouriteDTO favouriteDTO = new FavouriteDTO("27686136", "twitch");

        List<FavouriteDTO> returned = facade.saveFavourite(favouriteDTO, user1.getUserName());

        String actualID = returned.get(0).getChannelId();

        assertEquals(favouriteDTO.getChannelId(), actualID);
    }

    @Test
    public void testSaveFavouriteYoutubeAndTwitchSuccess() throws InvalidServiceException, AuthenticationException, InvalidInputException, NoResult, NotFound {
        FavouriteDTO favouriteTwitchDTO = new FavouriteDTO("27686136", "twitch");
        FavouriteDTO favouriteYoutubeDTO = new FavouriteDTO("UC-lHJZR3Gqxm24_Vd_AJ5Yw", "youtube");

        facade.saveFavourite(favouriteTwitchDTO, user2.getUserName());
        List<FavouriteDTO> returned = facade.saveFavourite(favouriteYoutubeDTO, user2.getUserName());

        String actualTwitchID = returned.get(0).getChannelId();
        String actualYoutubeID = returned.get(1).getChannelId();

        assertEquals(favouriteTwitchDTO.getChannelId(), actualTwitchID);
        assertEquals(favouriteYoutubeDTO.getChannelId(), actualYoutubeID);
    }

    @Test
    public void testSaveFavouriteInvalidService() {
        FavouriteDTO favouriteDTO = new FavouriteDTO("UC-lHJZR3Gqxm24_Vd_AJ5Yw", "invalidHERE");

        InvalidServiceException assertThrows = Assertions.assertThrows(InvalidServiceException.class, () -> {
            facade.saveFavourite(favouriteDTO, user1.getUserName());
        });

        Assertions.assertNotNull(assertThrows);
    }

    @Test
    public void testSaveFavouriteInvalidUsername() {
        FavouriteDTO favouriteDTO = new FavouriteDTO("UC-lHJZR3Gqxm24_Vd_AJ5Yw", "youtube");

        AuthenticationException assertThrows = Assertions.assertThrows(AuthenticationException.class, () -> {
            facade.saveFavourite(favouriteDTO, "invalidUsername");
        });

        Assertions.assertNotNull(assertThrows);
    }

    @Test
    public void testSaveFavouriteEmptyChannelName() {
        FavouriteDTO favouriteDTO = new FavouriteDTO("", "youtube");

        InvalidInputException assertThrows = Assertions.assertThrows(InvalidInputException.class, () -> {
            facade.saveFavourite(favouriteDTO, user1.getUserName());
        });

        Assertions.assertNotNull(assertThrows);
    }

    @Test
    public void testSaveFavouriteEmptyServiceName() {
        FavouriteDTO favouriteDTO = new FavouriteDTO("UC-lHJZR3Gqxm24_Vd_AJ5Yw", "");

        InvalidInputException assertThrows = Assertions.assertThrows(InvalidInputException.class, () -> {
            facade.saveFavourite(favouriteDTO, user1.getUserName());
        });

        Assertions.assertNotNull(assertThrows);
    }

    @Test
    public void testSaveFavouriteTwitchNoneExistingChannelId() {
        FavouriteDTO favouriteDTO = new FavouriteDTO("999999999", "twitch");

        NoResult assertThrows = Assertions.assertThrows(NoResult.class, () -> {
            facade.saveFavourite(favouriteDTO, user1.getUserName());
        });

        Assertions.assertNotNull(assertThrows);
    }

    @Test
    public void testSaveFavouriteYoutubeNoneExistingChannelId() {
        FavouriteDTO favouriteDTO = new FavouriteDTO("asdasd", "youtube");

        NotFound assertThrows = Assertions.assertThrows(NotFound.class, () -> {
            facade.saveFavourite(favouriteDTO, user1.getUserName());
        });

        Assertions.assertNotNull(assertThrows);
    }

    @Test
    public void testGetUserFavouritesOnSize() throws InvalidInputException, AuthenticationException, NoResult {
        int expectedSize = 2;

        List<FavouriteDTO> returned = facade.getUserFavourites(user1.getUserName());
        int actualSize = returned.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testGetUserFavouritesNoFavourites() throws InvalidInputException, AuthenticationException, NoResult {
        int expectedSize = 0;

        List<FavouriteDTO> returned = facade.getUserFavourites(user2.getUserName());
        int actualSize = returned.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testGetUserFavouritesWrongUserName() throws InvalidInputException, AuthenticationException, NoResult {
        AuthenticationException assertThrows = Assertions.assertThrows(AuthenticationException.class, () -> {
            facade.getUserFavourites("WrongUserName");
        });

        Assertions.assertNotNull(assertThrows);
    }
}
