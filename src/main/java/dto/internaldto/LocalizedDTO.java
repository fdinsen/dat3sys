
package dto.internaldto;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocalizedDTO implements Serializable
{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    private final static long serialVersionUID = -517216553276661415L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LocalizedDTO() {
    }

    /**
     * 
     * @param description
     * @param title
     */
    public LocalizedDTO(String title, String description) {
        super();
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalizedDTO withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalizedDTO withDescription(String description) {
        this.description = description;
        return this;
    }

}
