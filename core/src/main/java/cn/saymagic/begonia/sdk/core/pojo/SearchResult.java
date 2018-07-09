package cn.saymagic.begonia.sdk.core.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResult<T> {

    private int total;

    @SerializedName("total_pages")
    private int totalPages;

    private List<T> results;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
