package dto.internaldto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TwitchChannelDTO {

    @SerializedName("mature")
    @Expose
    private Boolean mature;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("broadcaster_language")
    @Expose
    private String broadcasterLanguage;
    @SerializedName("broadcaster_software")
    @Expose
    private String broadcasterSoftware;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("game")
    @Expose
    private String game;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("_id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("partner")
    @Expose
    private Boolean partner;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("video_banner")
    @Expose
    private Object videoBanner;
    @SerializedName("profile_banner")
    @Expose
    private Object profileBanner;
    @SerializedName("profile_banner_background_color")
    @Expose
    private String profileBannerBackgroundColor;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("views")
    @Expose
    private Integer views;
    @SerializedName("followers")
    @Expose
    private Integer followers;
    @SerializedName("broadcaster_type")
    @Expose
    private String broadcasterType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("private_video")
    @Expose
    private Boolean privateVideo;
    @SerializedName("privacy_options_enabled")
    @Expose
    private Boolean privacyOptionsEnabled;

    public Boolean getMature() {
        return mature;
    }

    public void setMature(Boolean mature) {
        this.mature = mature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBroadcasterLanguage() {
        return broadcasterLanguage;
    }

    public void setBroadcasterLanguage(String broadcasterLanguage) {
        this.broadcasterLanguage = broadcasterLanguage;
    }

    public String getBroadcasterSoftware() {
        return broadcasterSoftware;
    }

    public void setBroadcasterSoftware(String broadcasterSoftware) {
        this.broadcasterSoftware = broadcasterSoftware;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getPartner() {
        return partner;
    }

    public void setPartner(Boolean partner) {
        this.partner = partner;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Object getVideoBanner() {
        return videoBanner;
    }

    public void setVideoBanner(Object videoBanner) {
        this.videoBanner = videoBanner;
    }

    public Object getProfileBanner() {
        return profileBanner;
    }

    public void setProfileBanner(Object profileBanner) {
        this.profileBanner = profileBanner;
    }

    public String getProfileBannerBackgroundColor() {
        return profileBannerBackgroundColor;
    }

    public void setProfileBannerBackgroundColor(String profileBannerBackgroundColor) {
        this.profileBannerBackgroundColor = profileBannerBackgroundColor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getBroadcasterType() {
        return broadcasterType;
    }

    public void setBroadcasterType(String broadcasterType) {
        this.broadcasterType = broadcasterType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPrivateVideo() {
        return privateVideo;
    }

    public void setPrivateVideo(Boolean privateVideo) {
        this.privateVideo = privateVideo;
    }

    public Boolean getPrivacyOptionsEnabled() {
        return privacyOptionsEnabled;
    }

    public void setPrivacyOptionsEnabled(Boolean privacyOptionsEnabled) {
        this.privacyOptionsEnabled = privacyOptionsEnabled;
    }

}