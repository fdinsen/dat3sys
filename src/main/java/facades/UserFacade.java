package facades;

import dto.UserCredentials;
import entities.Role;
import entities.User;
import errorhandling.LoginInvalid;
import errorhandling.UsernameTaken;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import security.errorhandling.AuthenticationException;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public User createUser(UserCredentials uc) throws LoginInvalid, UsernameTaken {
        EntityManager em = emf.createEntityManager();
        if ("".equals(uc.getUsername()) || "".equals(uc.getPassword())) {
            throw new LoginInvalid("No username or password entered.");
        }
        User existingUser = em.find(User.class, uc.getUsername());
        if (existingUser != null) {
            throw new UsernameTaken(uc.getUsername()); 
        } else {

            em.getTransaction().begin();
            User user = new User(uc.getUsername(), uc.getPassword());

            Role role = em.find(Role.class, "user");
            if (role == null) {
                role = new Role("user");
                em.persist(role);
            }
            user.addRole(role);
            em.persist(user);

            em.getTransaction().commit();

            return user;
        }
    }

}
