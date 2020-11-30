/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto.internaldto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * This is a container for the returned data from YouTube
 */
public class YTSearchResultDTO {
    String nextPageToken;
    PageInfo pageInfo;
    List<IndividualResultDTO> items = new ArrayList(); 

    public YTSearchResultDTO() {
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<IndividualResultDTO> getItems() {
        return items;
    }

    public void setItems(List<IndividualResultDTO> items) {
        this.items = items;
    }  
}

class PageInfo {
    String totalResults;
    String resultsPerPage;

    public PageInfo() {
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(String resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }
    
    
}