
package dto.internaldto;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatisticsDTO implements Serializable
{

    @SerializedName("viewCount")
    @Expose
    private String viewCount;
    @SerializedName("subscriberCount")
    @Expose
    private String subscriberCount;
    @SerializedName("hiddenSubscriberCount")
    @Expose
    private boolean hiddenSubscriberCount;
    @SerializedName("videoCount")
    @Expose
    private String videoCount;
    private final static long serialVersionUID = -8126551356972555654L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public StatisticsDTO() {
    }

    /**
     * 
     * @param videoCount
     * @param subscriberCount
     * @param viewCount
     * @param hiddenSubscriberCount
     */
    public StatisticsDTO(String viewCount, String subscriberCount, boolean hiddenSubscriberCount, String videoCount) {
        super();
        this.viewCount = viewCount;
        this.subscriberCount = subscriberCount;
        this.hiddenSubscriberCount = hiddenSubscriberCount;
        this.videoCount = videoCount;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public StatisticsDTO withViewCount(String viewCount) {
        this.viewCount = viewCount;
        return this;
    }

    public String getSubscriberCount() {
        return subscriberCount;
    }

    public void setSubscriberCount(String subscriberCount) {
        this.subscriberCount = subscriberCount;
    }

    public StatisticsDTO withSubscriberCount(String subscriberCount) {
        this.subscriberCount = subscriberCount;
        return this;
    }

    public boolean isHiddenSubscriberCount() {
        return hiddenSubscriberCount;
    }

    public void setHiddenSubscriberCount(boolean hiddenSubscriberCount) {
        this.hiddenSubscriberCount = hiddenSubscriberCount;
    }

    public StatisticsDTO withHiddenSubscriberCount(boolean hiddenSubscriberCount) {
        this.hiddenSubscriberCount = hiddenSubscriberCount;
        return this;
    }

    public String getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(String videoCount) {
        this.videoCount = videoCount;
    }

    public StatisticsDTO withVideoCount(String videoCount) {
        this.videoCount = videoCount;
        return this;
    }

}
