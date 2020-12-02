/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import dto.TwitchAnalyticsDTO;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author gamma
 */
@Entity
@Table(name = "twitch_analytics")
public class TwitchAnalytics implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)
    @NotNull
    private String twitch_id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "game")
    private String game;
    @Basic(optional = false)
    @NotNull
    @Column(name = "views")
    private long views;
    @Basic(optional = false)
    @NotNull
    @Column(name = "followers")
    private long followers;
    @Temporal(TemporalType.DATE)
    private Date savedAt;
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private User savedBy;

    public TwitchAnalytics() {
    }
    
    public TwitchAnalytics(TwitchAnalyticsDTO twitch) {
        this.twitch_id = twitch.getId();
        game = twitch.getGame();
        views = twitch.getViews();
        followers = twitch.getFollowers();
        savedAt = twitch.getSavedOnDate();
        savedBy = twitch.getSavedBy();
    }

    
    public String getTwitch_id() {
        return twitch_id;
    }

    public void setTwitch_id(String id) {
        this.twitch_id = id;
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

    public Date getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Date savedAt) {
        this.savedAt = savedAt;
    }

    public User getSavedBy() {
        return savedBy;
    }

    public void setSavedBy(User savedBy) {
        this.savedBy = savedBy;
    }
    
    

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TwitchAnalytics)) {
            return false;
        }
        TwitchAnalytics other = (TwitchAnalytics) object;
        if ((this.twitch_id == null && other.twitch_id != null) || (this.twitch_id != null && !this.twitch_id.equals(other.twitch_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.YouTubeAnalytics[ id=" + twitch_id + " ]";
    }
    
}
