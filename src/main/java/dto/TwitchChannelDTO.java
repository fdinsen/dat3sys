package dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TwitchChannelDTO {
    @SerializedName(value = "country", alternate = "broadcaster_language")
    @Expose
    private String country;
    @SerializedName(value = "title", alternate = "display_name")
    @Expose
    private String title;
    @SerializedName("game")
    @Expose
    private String game;
    @SerializedName(value = "id", alternate = "_id")
    @Expose
    private String id;
    @SerializedName("partner")
    @Expose
    private Boolean partner;
    @SerializedName(value = "profilePicUrl", alternate = "logo")
    @Expose
    private String profilePicUrl;
    @SerializedName("views")
    @Expose
    private Integer views;
    @SerializedName("followers")
    @Expose
    private Integer followers;
    @SerializedName(value = "desc", alternate = "description")
    @Expose
    private String desc;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getPartner() {
        return partner;
    }

    public void setPartner(Boolean partner) {
        this.partner = partner;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }


    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
