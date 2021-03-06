/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.TwitchAnalytics;
import entities.User;
import java.util.Date;

/**
 *
 * @author gamma
 */
public class TwitchAnalyticsDTO {
    private String id;
    private String game;
    private long views;
    private long followers;
    private Date savedOnDate;

    public TwitchAnalyticsDTO() {
        savedOnDate = new Date();
    }
    
    public TwitchAnalyticsDTO(TwitchAnalytics twitch) {
        this.id = twitch.getChannelName();
        this.game = twitch.getGame();
        this.views = twitch.getViews();
        this.followers = twitch.getFollowers();
        this.savedOnDate = twitch.getSavedAt();
    }

    public TwitchAnalyticsDTO(TwitchChannelDTO twitch, Date savedOndate) {
        this.id = twitch.getId();
        this.game = twitch.getGame();
        this.views = twitch.getViews();
        this.followers = twitch.getFollowers();
        this.savedOnDate = savedOndate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public long getFollowers() {
        return followers;
    }

    public void setFollowers(long followers) {
        this.followers = followers;
    }

    public Date getSavedOnDate() {
        return savedOnDate;
    }

    public void setSavedOnDate(Date savedOnDate) {
        this.savedOnDate = savedOnDate; 
    } 
}
