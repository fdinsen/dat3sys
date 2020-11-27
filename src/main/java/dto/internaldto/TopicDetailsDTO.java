package dto.internaldto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopicDetailsDTO implements Serializable
{

    @SerializedName("topicIds")
    @Expose
    private List<String> topicIds = new ArrayList<String>();
    @SerializedName("topicCategories")
    @Expose
    private List<String> topicCategories = new ArrayList<String>();
    private final static long serialVersionUID = -1249291488977908654L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TopicDetailsDTO() {
    }

    /**
     * 
     * @param topicIds
     * @param topicCategories
     */
    public TopicDetailsDTO(List<String> topicIds, List<String> topicCategories) {
        super();
        this.topicIds = topicIds;
        this.topicCategories = topicCategories;
    }

    public List<String> getTopicIds() {
        return topicIds;
    }

    public void setTopicIds(List<String> topicIds) {
        this.topicIds = topicIds;
    }

    public TopicDetailsDTO withTopicIds(List<String> topicIds) {
        this.topicIds = topicIds;
        return this;
    }

    public List<String> getTopicCategories() {
        return topicCategories;
    }

    public void setTopicCategories(List<String> topicCategories) {
        this.topicCategories = topicCategories;
    }

    public TopicDetailsDTO withTopicCategories(List<String> topicCategories) {
        this.topicCategories = topicCategories;
        return this;
    }

}
