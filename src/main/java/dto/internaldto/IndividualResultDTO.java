/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto.internaldto;

/**
 *
 * This is a container for each individual search result returned from YouTube
 */
public class IndividualResultDTO {
    String kind;
    String etag;
    SnippetDTO snippet;

    public IndividualResultDTO() {
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public SnippetDTO getSnippet() {
        return snippet;
    }

    public void setSnippet(SnippetDTO snippet) {
        this.snippet = snippet;
    }
}
