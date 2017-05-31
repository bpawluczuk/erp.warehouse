package pl.itse.erp.cqrs.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Borys on 2017-04-15.
 */
public class QueryPaginatedResult<T> implements Serializable {

    private final List<T> items;
    private final int pageSize;
    private final int pageNumber;
    private final int pagesCount;
    private final Long totalItemsCount;

    public QueryPaginatedResult(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        items = Collections.emptyList();
        pagesCount = 0;
        pages = Collections.emptyList();
        totalItemsCount = 0L;
    }

    public QueryPaginatedResult(List<T> items, int pageNumber, int pageSize, Long totalItemsCount) {
        this.items = items;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.pagesCount = countPages(pageSize, totalItemsCount);
        this.pages = pages();
        this.totalItemsCount = totalItemsCount;
    }

    private int countPages(int size, Long itemsCount) {
        return (int) Math.ceil((double) itemsCount / size);
    }

    private List<Integer> pages() {
        List<Integer> pg = new ArrayList<Integer>();
        for (int i = 1; i <= pagesCount; i++) {
            pg.add(i);
        }
        return pg;
    }

    private List<Integer> pages;

    public List<Integer> getPages() {
        return pages;
    }

    public List<T> getItems() {
        return items;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public Long getTotalItemsCount() {
        return totalItemsCount;
    }
}
