package facades;

import com.google.gson.Gson;
import dto.FavouriteDTO;
import entities.Favourite;
import entities.User;
import errorhandling.*;
import security.errorhandling.AuthenticationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class FavouriteFacade {
    private static FavouriteFacade instance;
    private static EntityManagerFactory emf;
    private static String url;
    private static Gson GSON = new Gson();

    //Private Constructor to ensure Singleton
    private FavouriteFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static FavouriteFacade getFavouriteFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FavouriteFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Favourite> saveFavourite(FavouriteDTO favouriteDTO, String username) throws AuthenticationException, InvalidServiceException, InvalidInputException, NoResult, NotFound {
        EntityManager em = getEntityManager();

        if(favouriteDTO.getChannelId() == null || favouriteDTO.getChannelId().isEmpty() || favouriteDTO.getService() == null || favouriteDTO.getService().isEmpty()){
            throw new InvalidInputException();
        }

        //Check if service type is correct and channel id exist
        if(favouriteDTO.getService() == "twitch"){
            new TwitchFacade().getTwitchChannel(favouriteDTO.getChannelId());
        }else if(favouriteDTO.getService() == "youtube"){
            new YoutubeFacade().getChannelById(favouriteDTO.getChannelId());
        }else{
            throw new InvalidServiceException(favouriteDTO.getService());
        }

        User user = (User) em.createQuery(
                "SELECT u FROM User u WHERE u.userName LIKE :username")
                .setParameter("username", username)
                .getSingleResult();

        //If current user is found
        if(user != null){
            //Add favorite to user favorite list
            List<Favourite> userFavourites = user.getFavouriteList();
            Favourite favourite = new Favourite(favouriteDTO.getChannelId(), favouriteDTO.getUsername());
            userFavourites.add(favourite);
            user.setFavouriteList(userFavourites);

            try{
                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();
            }finally{
                em.close();
            }

            return user.getFavouriteList();
        }else{
            //No user found
            throw new AuthenticationException("Your username is not found");
        }
    }
}
