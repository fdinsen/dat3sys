/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.User;
import entities.YouTubeAnalytics;
import java.util.Date;

/**
 *
 * @author gamma
 */
public class YouTubeAnalyticsDTO {
    private String id;
    private long views;
    private long subscribers;
    private long videoCount;
    private Date savedOnDate;
    private User savedBy;

    public YouTubeAnalyticsDTO() {
        savedOnDate = new Date();
    }
    
    public YouTubeAnalyticsDTO(YouTubeAnalytics yt) {
        id = yt.getChannelId();
        views = yt.getViews();
        subscribers = yt.getSubscribers();
        videoCount = yt.getVideoCount();
        savedOnDate = yt.getSavedAt();
        savedBy = yt.getSavedBy();
    }

    public YouTubeAnalyticsDTO(YoutubeResultDTO youTubeRes, Date date, User user) {
        id = youTubeRes.getId();
        views = youTubeRes.getViews();
        subscribers = youTubeRes.getSubscribers();
        videoCount = youTubeRes.getVideoCount();
        savedOnDate = date;
        savedBy = user;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public long getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(long subscribers) {
        this.subscribers = subscribers;
    }

    public long getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(long videoCount) {
        this.videoCount = videoCount;
    }

    public Date getSavedOnDate() {
        return savedOnDate;
    }

    public void setSavedOnDate(Date savedOnDate) {
        this.savedOnDate = savedOnDate; 
    }

    public User getSavedBy() {
        return savedBy;
    }

    public void setSavedBy(User savedBy) {
        this.savedBy = savedBy;
    }
    
    
    
}
