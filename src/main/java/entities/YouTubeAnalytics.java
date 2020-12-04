/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import dto.YouTubeAnalyticsDTO;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
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
@Table(name = "youtube_analytics")
public class YouTubeAnalytics implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "channel_id")
    private String channelId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "views")
    private long views;
    @Basic(optional = false)
    @NotNull
    @Column(name = "subscribers")
    private long subscribers;
    @Basic(optional = false)
    @NotNull
    @Column(name = "video_count")
    private long videoCount;
    @Temporal(TemporalType.DATE)
    private Date savedAt;
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private User savedBy;

    public YouTubeAnalytics() {
    }
    
    public YouTubeAnalytics(YouTubeAnalyticsDTO yt, User user) {
        this.channelId = yt.getId();
        this.views = yt.getViews();
        this.subscribers = yt.getSubscribers();
        this.videoCount = yt.getVideoCount();
        this.savedAt = yt.getSavedOnDate();
        this.savedBy = user;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
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
        if (!(object instanceof YouTubeAnalytics)) {
            return false;
        }
        YouTubeAnalytics other = (YouTubeAnalytics) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.YouTubeAnalytics[ id=" + id + " ]";
    }
    
}
