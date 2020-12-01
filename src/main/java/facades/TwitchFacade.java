package facades;

import com.google.gson.Gson;
import dto.SearchResultsDTO;
import dto.TwitchAnalyticsDTO;
import dto.TwitchChannelDTO;
import dto.internaldto.TwitchSearchResultsDTO;
import entities.TwitchAnalytics;
import entities.User;
import errorhandling.NoResult;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import errorhandling.TooRecentSaveException;
import utils.HttpUtils;

public class TwitchFacade {

    private static TwitchFacade instance;
    private static EntityManagerFactory emf;
    private static String url;
    private static Gson GSON = new Gson();

    //Private Constructor to ensure Singleton
    private TwitchFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static TwitchFacade getTwitchFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TwitchFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public SearchResultsDTO searchTwitch(String query) throws NoResult {
        if (query == null || "".equals(query)) {
            throw new NoResult(query);
        } else {
            try {
                String json = HttpUtils.fetchTwitchData("https://api.twitch.tv/kraken/search/channels?query=" + query);
                TwitchSearchResultsDTO result = GSON.fromJson(json, TwitchSearchResultsDTO.class);
                SearchResultsDTO dto = new SearchResultsDTO(result);
                if(dto.getAll().isEmpty()) {
                    throw new NoResult(query);
                }
                return dto;
            } catch (IOException ex) {
                Logger.getLogger(TwitchFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public TwitchChannelDTO getTwitchChannel(String id) throws NoResult {
        if (id == null || "".equals(id)) {
            throw new NoResult(id);
        } else {
            try {
                String json = HttpUtils.fetchTwitchData("https://api.twitch.tv/kraken/channels/" + id.toString());
                TwitchChannelDTO twitchChannelDTO = GSON.fromJson(json, TwitchChannelDTO.class);

                if(twitchChannelDTO.getId().isEmpty() || twitchChannelDTO.getId() == null) {
                    throw new NoResult(id);
                }
                return twitchChannelDTO;
            } catch (IOException ex) {
                Logger.getLogger(TwitchFacade.class.getName()).log(Level.SEVERE, null, ex);
                throw new NoResult(id);
            }
        }
    }

    public List<TwitchAnalytics> saveTwitchAnalytics(String id) throws NoResult, TooRecentSaveException {
                EntityManager em = getEntityManager();
                //check time interval
                List<TwitchAnalytics>   twitchAnalyticsList = em.createQuery(
                                        "SELECT ta FROM TwitchAnalytics ta WHERE ta.id LIKE :id")
                                        .setParameter("id", id)
                                        .getResultList();

                if(twitchAnalyticsList.size() > 0){
                    //Check if latest entry is older than 1 minute
                    Date currentDate = new Date();
                    Date twitchDate = twitchAnalyticsList.get(twitchAnalyticsList.size() - 1).getSavedAt();
                    long min1 = 60l * 1000;
                    if(currentDate.before(new Date((twitchDate .getTime() + min1)))){
                        throw new TooRecentSaveException();
                    }

                }



                TwitchChannelDTO twitchChannelDTO = getTwitchChannel(id);


                //Temp user
                User user = em.find(User.class, "user");


                TwitchAnalyticsDTO twitchAnalyticsDTO = new TwitchAnalyticsDTO(twitchChannelDTO,new Date(), user);
                TwitchAnalytics twitchAnalytics = new TwitchAnalytics(twitchAnalyticsDTO);

                try{
                    em.getTransaction().begin();
                    em.persist(twitchAnalytics);
                    em.getTransaction().commit();
                }finally{
                    em.close();
                }
                twitchAnalyticsList.add(twitchAnalytics);



                return twitchAnalyticsList;
    }

}
