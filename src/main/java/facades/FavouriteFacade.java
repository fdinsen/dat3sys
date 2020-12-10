package facades;

import com.google.gson.Gson;
import dto.FavouriteDTO;
import dto.TwitchChannelDTO;
import entities.Favourite;
import entities.User;
import errorhandling.*;
import security.errorhandling.AuthenticationException;
import utils.HttpUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public List<FavouriteDTO> saveFavourite(FavouriteDTO favouriteDTO, String username) throws AuthenticationException, InvalidServiceException, InvalidInputException, NoResult, NotFound {
        EntityManager em = getEntityManager();

        if(favouriteDTO == null || favouriteDTO.getChannelId() == null || favouriteDTO.getChannelId().isEmpty() || favouriteDTO.getService() == null || favouriteDTO.getService().isEmpty()){
            throw new InvalidInputException();
        }

        //Check if service type is correct and channel id exist
        if(favouriteDTO.getService().equals("twitch")){
            new TwitchFacade().getTwitchChannel(favouriteDTO.getChannelId());
        }else if(favouriteDTO.getService().equals("youtube")){
            new YoutubeFacade().getChannelById(favouriteDTO.getChannelId());
        }else{
            throw new InvalidServiceException(favouriteDTO.getService());
        }

        User user;
        try{
            user = (User) em.createQuery(
                    "SELECT u FROM User u WHERE u.userName LIKE :username")
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            throw new AuthenticationException("Your username is not found");
        }


            //Add favorite to user favorite list
            List<Favourite> userFavourites = user.getFavouriteList();
            Favourite favourite = new Favourite(favouriteDTO.getChannelId(), favouriteDTO.getService());

        //If the already have added the channel id, do nothing
            boolean exist = false;
            for (Favourite favour : userFavourites){
                if(favour.getChannelId().equals(favourite.getChannelId()) && favour.getService().equals(favourite.getService())){
                    exist = true;
                    break;
                }
            }


            if(!exist){
                userFavourites.add(favourite);
                user.setFavouriteList(userFavourites);

                try{
                    em.getTransaction().begin();
                    em.persist(user);
                    em.getTransaction().commit();
                }finally{
                    em.close();
                }
            }

            List<FavouriteDTO> favouriteDTOList = new LinkedList<>();
            for (Favourite favour: user.getFavouriteList()){
                FavouriteDTO favouriteDTO1 = new FavouriteDTO(favour.getChannelId(),favour.getService());
                favouriteDTOList.add(favouriteDTO1);
            }

            return favouriteDTOList;
    }

    public List<FavouriteDTO> getUserFavourites(String username) throws NoResult, InvalidInputException, AuthenticationException {
        if (username == null || "".equals(username)) {
            throw new InvalidInputException();
        } else {
            EntityManager em = getEntityManager();

            //Check if user exist
            User user;
            try{
                user = (User) em.createQuery(
                        "SELECT u FROM User u WHERE u.userName LIKE :username")
                        .setParameter("username", username)
                        .getSingleResult();
            } catch (Exception e) {
                throw new AuthenticationException("No User Found");
            }

            List<Favourite> userFavourites = user.getFavouriteList();
            List<FavouriteDTO> favouriteDTOList = new ArrayList<>();
            for (Favourite favour: userFavourites){
                FavouriteDTO favouriteDTO = new FavouriteDTO(favour.getChannelId(),favour.getService());
                favouriteDTOList.add(favouriteDTO);
            }

            return favouriteDTOList;
        }
    }
}
