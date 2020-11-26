package dto.internaldto;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TwitchSearchResultsDTO {

    @SerializedName("_total")
    @Expose
    private Integer total;
    @SerializedName("channels")
    @Expose
    private List<TwitchChannelDTO> channels = null;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<TwitchChannelDTO> getChannels() {
        return channels;
    }

    public void setChannels(List<TwitchChannelDTO> channels) {
        this.channels = channels;
    }

}