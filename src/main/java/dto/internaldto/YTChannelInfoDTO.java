
package dto.internaldto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YTChannelInfoDTO implements Serializable
{

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("items")
    @Expose
    private List<ItemDTO> items = new ArrayList<ItemDTO>();
    private final static long serialVersionUID = -1916071973772128556L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public YTChannelInfoDTO() {
    }

    /**
     * 
     * @param kind
     * @param pageInfo
     * @param etag
     * @param items
     */
    public YTChannelInfoDTO(String kind, String etag, List<ItemDTO> items) {
        super();
        this.kind = kind;
        this.etag = etag;
        this.items = items;
    }
    
    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public YTChannelInfoDTO withKind(String kind) {
        this.kind = kind;
        return this;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public YTChannelInfoDTO withEtag(String etag) {
        this.etag = etag;
        return this;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    public YTChannelInfoDTO withItems(List<ItemDTO> items) {
        this.items = items;
        return this;
    }

}
