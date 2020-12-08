/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.UserCredentials;
import dto.YoutubeResultDTO;
import entities.User;
import errorhandling.LoginInvalid;
import errorhandling.NotFound;
import errorhandling.UsernameTaken;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import utils.SetupTestAnalytics;

/**
 *
 * @author gamma
 */
public class UserFacadeTest {

    private static EntityManagerFactory emf;
    private static UserFacade facade;

    public UserFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = UserFacade.getUserFacade(emf);
        EntityManager em = emf.createEntityManager();
        try {

            em.getTransaction().begin();
            //Delete existing users and roles to get a "fresh" database
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void testCreateUserSuccessNewRole() throws LoginInvalid, UsernameTaken {
        //Arrange
        String expRole = "user";
        String uname = "person1";

        //Act
        UserCredentials uc = new UserCredentials(uname, "pass23");
        User user = facade.createUser(uc);

        assertEquals(uname, user.getUserName());
        assertEquals(expRole, user.getRoleList().get(0).getRoleName());
    }

    @Test
    public void testCreateUserSuccessExistingRole() throws LoginInvalid, UsernameTaken {
        //Arrange
        String expRole = "user";
        String uname = "person2";

        //Act
        UserCredentials uc = new UserCredentials(uname, "pass232");
        User user = facade.createUser(uc);

        assertEquals(uname, user.getUserName());
        assertEquals(expRole, user.getRoleList().get(0).getRoleName());
    }

    @Test
    public void testCreateUserExistingUser() throws LoginInvalid, UsernameTaken {
        UsernameTaken assertThrows;

        //Act
        UserCredentials uc = new UserCredentials("person3", "pass23s2");
        User userA = facade.createUser(uc);

        assertThrows = Assertions.assertThrows(UsernameTaken.class, () -> {
            User userB = facade.createUser(uc);
        });
        Assertions.assertNotNull(assertThrows);
    }

    @Test
    public void testCreateUserNoName() throws LoginInvalid {
        //Arrange
        LoginInvalid assertThrows;

        //Act
        UserCredentials uc = new UserCredentials("", "pass23");

        assertThrows = Assertions.assertThrows(LoginInvalid.class, () -> {
            User user = facade.createUser(uc);
        });
        Assertions.assertNotNull(assertThrows);
    }

    @Test
    public void testCreateUserNoPass() throws LoginInvalid {
        //Arrange
        LoginInvalid assertThrows;

        //Act
        UserCredentials uc = new UserCredentials("person1", "");

        assertThrows = Assertions.assertThrows(LoginInvalid.class, () -> {
            User user = facade.createUser(uc);
        });
        Assertions.assertNotNull(assertThrows);
    }

    @Test
    public void testCreateUserNameNull() throws LoginInvalid {
        //Arrange
        LoginInvalid assertThrows;

        //Act
        UserCredentials uc = new UserCredentials(null, "pass23");

        assertThrows = Assertions.assertThrows(LoginInvalid.class, () -> {
            User user = facade.createUser(uc);
        });
        Assertions.assertNotNull(assertThrows);
    }

    @Test
    public void testCreateUserPassNull() throws LoginInvalid {
        //Arrange
        LoginInvalid assertThrows;

        //Act
        UserCredentials uc = new UserCredentials("person1", null);

        assertThrows = Assertions.assertThrows(LoginInvalid.class, () -> {
            User user = facade.createUser(uc);
        });
        Assertions.assertNotNull(assertThrows);
    }

}
