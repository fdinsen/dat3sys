package dto;

public class FavouriteDTO {
    private String username;
    private String channelId;
    private String service;

    public FavouriteDTO(String channelId, String service) {
        this.channelId = channelId;
        this.service = service;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
