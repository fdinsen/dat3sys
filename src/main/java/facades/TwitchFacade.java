package facades;

import com.google.gson.Gson;
import dto.SearchResultsDTO;
import dto.TwitchChannelDTO;
import dto.internaldto.TwitchSearchResultsDTO;
import errorhandling.NoResult;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import utils.HttpUtils;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
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

}
