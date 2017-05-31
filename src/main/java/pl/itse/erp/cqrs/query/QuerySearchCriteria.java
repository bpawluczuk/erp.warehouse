package pl.itse.erp.cqrs.query;

/**
 * Created by Borys on 2017-05-04.
 */
public class QuerySearchCriteria {

    private int pageNumber;
    private int itemsPerPage;
    private int offset;
    private int limit;

    public QuerySearchCriteria(int pageNumber, int itemsPerPage) {
        setPageNumber(pageNumber);
        setItemsPerPage(itemsPerPage);
        this.offset = getItemsPerPage() * (getPageNumber() - 1);
        this.limit = getItemsPerPage();
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        if (pageNumber < 1) {
            this.pageNumber = 1;
        } else {
            this.pageNumber = pageNumber;
        }
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        if (itemsPerPage < 1) {
            this.itemsPerPage = 1;
        } else {
            this.itemsPerPage = itemsPerPage;
        }
    }
}
