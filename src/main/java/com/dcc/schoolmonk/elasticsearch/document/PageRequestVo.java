package com.dcc.schoolmonk.elasticsearch.document;

public class PageRequestVo {
    private int page;
    private int size;
    private String sortColumn;
    private String sortOrder;

    private static final int DEFAULT_SIZE = 10;

    public PageRequestVo(int page, int size, String sortColumn, String sortOrder) {
        this.page = page;
        this.size = size;
        this.sortColumn = sortColumn;
        this.sortOrder = sortOrder;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public PageRequestVo() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size != 0 ? size : DEFAULT_SIZE;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
