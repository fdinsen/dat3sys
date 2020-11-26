/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import dto.internaldto.YTSearchResultDTO;
import java.util.ArrayList;
import java.util.List;
import javax.naming.directory.SearchResult;

/**
 *
 * @author gamma
 */
public class SearchResultsDTO {
    private List<SearchResultDTO> all = new ArrayList();

    public SearchResultsDTO() {
    }
    
    public SearchResultsDTO(YTSearchResultDTO sr) {
        for(int i = 0; i < sr.getItems().size(); i++) {
            String channelId = sr.getItems().get(i).getSnippet().getChannelId();
            String name = sr.getItems().get(i).getSnippet().getTitle();
            String pfpurl = sr.getItems().get(i).getSnippet().getThumbnails().getHigh().getUrl();
            all.add(new SearchResultDTO(channelId, name, pfpurl));
        }
    }

    public List<SearchResultDTO> getAll() {
        return all;
    }

    public void setAll(List<SearchResultDTO> all) {
        this.all = all;
    }
    
    public void addSearchResult(SearchResultDTO sr) {
        all.add(sr);
    }
    
}
