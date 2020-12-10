package facades;

import com.google.gson.Gson;
import dto.*;
import dto.internaldto.YTChannelInfoDTO;
import dto.internaldto.YTSearchResultDTO;
import entities.TwitchAnalytics;
import entities.User;
import entities.YouTubeAnalytics;
import errorhandling.NoResult;
import errorhandling.NotFound;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import errorhandling.TooRecentSaveException;
import java.util.ArrayList;
import javax.persistence.TypedQuery;
import utils.APIKeyHandler;
import utils.HttpUtils;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class YoutubeFacade {

    private static YoutubeFacade instance;
    private static EntityManagerFactory emf;
    private static String url;
    private static Gson GSON = new Gson();
    private static boolean runningThroughGrizzly = false;

    //Private Constructor to ensure Singleton
    YoutubeFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static YoutubeFacade getYoutubeFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new YoutubeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public SearchResultsDTO searchYouTube(String query) throws NoResult {
        if (query == null || "".equals(query)) {
            throw new NoResult(query);
        } else {
            try {
                String key = APIKeyHandler.getYouTubeKey();
                String yturl = getSearchURL(query, key);
                String json = HttpUtils.fetchData(yturl);
                YTSearchResultDTO result = GSON.fromJson(json, YTSearchResultDTO.class);
                SearchResultsDTO dto = new SearchResultsDTO(result);
                if (dto.getAll().isEmpty()) {
                    throw new NoResult(query);
                }
                return dto;
            } catch (IOException ex) {
                //TODO throw an exception to show that the server can't be found
                Logger.getLogger(YoutubeFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public YoutubeResultDTO getChannelById(String id) throws NotFound {
        if (id == null || "".equals(id)) {
            throw new NotFound(id);
        } else {
            try {
                String key = APIKeyHandler.getYouTubeKey();
                String ytUrl = getChannelUrl(id, key);
                
                String json = HttpUtils.fetchData(ytUrl);
                
                YTChannelInfoDTO result = GSON.fromJson(json, YTChannelInfoDTO.class);
                
                YoutubeResultDTO dto = new YoutubeResultDTO(result);
                if(dto.getTopicCategories().isEmpty()){
                    throw new NotFound(id);
                }
                return dto;
            } catch (IOException ex) {
                Logger.getLogger(YoutubeFacade.class.getName()).log(Level.SEVERE, null, ex);
                throw new NotFound(id);
            } catch(IndexOutOfBoundsException e){
                throw new NotFound(id);
            }
        }
    }

    private String getChannelUrl(String id, String key) {
        if (isJUnitTest() || runningThroughGrizzly) {
            return "https://fdinsen.com/testServerReplacement/api/xxx/ytget/" + id;
        } else {
            String youtubeBase = "https://youtube.googleapis.com/youtube/v3/channels?part=status&part=statistics&part=id&part=contentDetails&part=brandingSettings&part=localizations&part=snippet&part=topicDetails&part=contentOwnerDetails";
            String idParameter = "&id=" + id;
            String keyParameter = "&key=" + key;
            return youtubeBase + idParameter + keyParameter;
        }
    }

    private String getSearchURL(String query, String key) {
        if (isJUnitTest() || runningThroughGrizzly) {
            return "https://fdinsen.com/testServerReplacement/api/xxx/ytsearch/" + query;
        } else {
            String url1 = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&q=";
            String url2 = "&type=channel&key=";
            return url1 + query + url2 + key;
        }
    }

    public List<YouTubeAnalytics> saveYoutubeAnalytics(String id) throws NoResult, TooRecentSaveException, NotFound {
        EntityManager em = getEntityManager();
        //check time interval
        List<YouTubeAnalytics>   youTubeAnalyticsList = em.createQuery(
                "SELECT ya FROM YouTubeAnalytics ya WHERE ya.channelId LIKE :id")
                .setParameter("id", id)
                .getResultList();

        if(youTubeAnalyticsList.size() > 0){
            //Check if latest entry is older than 1 minute
            Date currentDate = new Date();
            Date twitchDate = youTubeAnalyticsList.get(youTubeAnalyticsList.size() - 1).getSavedAt();
            long min1 = 60l * 1000;
            if(currentDate.before(new Date((twitchDate .getTime() + min1)))){
                throw new TooRecentSaveException();
            }

        }



        YoutubeResultDTO youTubeRes = getChannelById(id);
        youTubeRes.setId(id);

        YouTubeAnalyticsDTO youTubeAnalyticsDTO = new YouTubeAnalyticsDTO(youTubeRes,new Date());
        YouTubeAnalytics youtubeAnalytics = new YouTubeAnalytics(youTubeAnalyticsDTO);

        try{
            em.getTransaction().begin();
            em.persist(youtubeAnalytics);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        youTubeAnalyticsList.add(youtubeAnalytics);



        return youTubeAnalyticsList;
    }
    
    public List<YouTubeAnalyticsDTO> getYouTubeAnalytics(String channelId) throws NotFound {
        EntityManager em = emf.createEntityManager();
        TypedQuery<YouTubeAnalytics> query = em.createQuery("SELECT yt FROM YouTubeAnalytics yt WHERE yt.channelId = :channel ORDER BY yt.savedAt", YouTubeAnalytics.class);
        query.setParameter("channel", channelId);
        List<YouTubeAnalyticsDTO> list = new ArrayList();
        
        query.getResultStream().forEach(element -> {
            list.add(new YouTubeAnalyticsDTO(element));
        });
        
        if (list.isEmpty()) {
            throw new NotFound(channelId);
        }
        
        return list;
    }

    public static boolean isJUnitTest() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }

    public static void runThroughGrizzly() {
        runningThroughGrizzly = true;
    }

}
