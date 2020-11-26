package facades;

import com.google.gson.Gson;
import dto.SearchResultsDTO;
import dto.internaldto.YTSearchResultDTO;
import entities.RenameMe;
import errorhandling.NoResult;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
    private YoutubeFacade() {
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

    //TODO Remove/Change this before use
    public long getRenameMeCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long renameMeCount = (long) em.createQuery("SELECT COUNT(r) FROM RenameMe r").getSingleResult();
            return renameMeCount;
        } finally {
            em.close();
        }

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

    private String getSearchURL(String query, String key) {
        if (isJUnitTest() || runningThroughGrizzly) {
            return "https://fdinsen.com/testServerReplacement/api/xxx/ytsearch/" + query;
        } else {
            String url1 = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&q=";
            String url2 = "&type=channel&key=";
            return url1 + query + url2 + key;
        }
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
