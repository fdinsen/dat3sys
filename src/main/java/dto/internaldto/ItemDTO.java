
package dto.internaldto;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemDTO implements Serializable
{

    @SerializedName("kind")
    @Expose
    private String kind;
    
    @SerializedName("etag")
    @Expose
    private String etag;
    
    @SerializedName("id")
    @Expose
    private String id;
    
    @SerializedName("snippet")
    @Expose
    private YTSnippetDTO snippet;
    
    @SerializedName("statistics")
    @Expose
    private StatisticsDTO statistics;
    
    @SerializedName("topicDetails")
    @Expose
    private TopicDetailsDTO topicDetails;
    
    private final static long serialVersionUID = 7777741913777331943L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ItemDTO() {
    }

    /**
     * 
     * @param snippet
     * @param topicDetails
     * @param kind
     * @param etag
     * @param id
     * @param statistics
     */
    public ItemDTO(String kind, String etag, String id, YTSnippetDTO snippet, StatisticsDTO statistics, TopicDetailsDTO topicDetails) {
        super();
        this.kind = kind;
        this.etag = etag;
        this.id = id;
        this.snippet = snippet;
        this.statistics = statistics;
        this.topicDetails = topicDetails;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public ItemDTO withKind(String kind) {
        this.kind = kind;
        return this;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public ItemDTO withEtag(String etag) {
        this.etag = etag;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ItemDTO withId(String id) {
        this.id = id;
        return this;
    }

    public YTSnippetDTO getSnippet() {
        return snippet;
    }

    public void setSnippet(YTSnippetDTO snippet) {
        this.snippet = snippet;
    }

    public ItemDTO withSnippet(YTSnippetDTO snippet) {
        this.snippet = snippet;
        return this;
    }

    public StatisticsDTO getStatistics() {
        return statistics;
    }

    public void setStatistics(StatisticsDTO statistics) {
        this.statistics = statistics;
    }

    public ItemDTO withStatistics(StatisticsDTO statistics) {
        this.statistics = statistics;
        return this;
    }

    public TopicDetailsDTO getTopicDetails() {
        return topicDetails;
    }

    public void setTopicDetails(TopicDetailsDTO topicDetails) {
        this.topicDetails = topicDetails;
    }

    public ItemDTO withTopicDetails(TopicDetailsDTO topicDetails) {
        this.topicDetails = topicDetails;
        return this;
    }
}
