/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import dto.internaldto.YTChannelInfoDTO;
import java.util.List;

/**
 *
 * @author simon
 */
public class YoutubeResultDTO {
    private String id;
    private String title;
    private String desc;
    private String profilePicURL;
    private String country;
    private long views;
    private long subscribers;
    private long videoCount;
    private List<String> topicCategories;
    
    public YoutubeResultDTO(YTChannelInfoDTO result) {
        this.id = result.getItems().get(0).getSnippet().getChannelId();
        this.title = result.getItems().get(0).getSnippet().getTitle();
        this.desc = result.getItems().get(0).getSnippet().getDescription();
        this.profilePicURL = result.getItems().get(0).getSnippet().getThumbnails().getHigh().getUrl();
        this.country = result.getItems().get(0).getSnippet().getCountry();
        this.views = Long.parseLong(result.getItems().get(0).getStatistics().getViewCount());
        this.subscribers = Long.parseLong(result.getItems().get(0).getStatistics().getSubscriberCount());
        this.videoCount = Long.parseLong(result.getItems().get(0).getStatistics().getVideoCount());
        this.topicCategories = result.getItems().get(0).getTopicDetails().getTopicCategories();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getProfilePicURL() {
        return profilePicURL;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public long getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(int subscribers) {
        this.subscribers = subscribers;
    }

    public long getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    public List<String> getTopicCategories() {
        return topicCategories;
    }

    public void setTopicCategories(List<String> topicCategories) {
        this.topicCategories = topicCategories;
    }

    @Override
    public String toString() {
        
        String topicCat = "";
        
        for (String topicCategory : topicCategories) {
            topicCat += topicCategory + " ";
        }
        
        return "Title: " + title + "\ndesc: " + desc + "\nprofilepic: " + profilePicURL + "\ncountry: " + country + "\nViews: " + views + "\nsubscribers: " + subscribers + "\nVideo count: "+videoCount+ "\n"+topicCat;
        
//        private long views;
//    private long subscribers;
//    private long videoCount;
//    private List<String> topicCategories;
    }
    
    
}
