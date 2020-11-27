/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto.internaldto;

/**
 *
 * This is a container for each ID element in the returned data from the YouTube API
 */
public class YTSnippetDTO {
    String title;
    String channelId;
    String description;
    YTProfilePicDTO thumbnails;
    String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public YTSnippetDTO() {
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public YTProfilePicDTO getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(YTProfilePicDTO thumbnails) {
        this.thumbnails = thumbnails;
    }
    
    

    
}
