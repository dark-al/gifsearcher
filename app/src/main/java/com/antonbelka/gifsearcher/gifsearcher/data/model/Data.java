package com.antonbelka.gifsearcher.gifsearcher.data.model;

import java.util.List;

/**
 * Immutable model class for a Data.
 */
public final class Data {

    List<Gif> data;
    Pagination pagination;

    public Data(List<Gif> data, Pagination pagination) {
        this.data = data;
        this.pagination = pagination;
    }

    public List<Gif> getData() {
        return data;
    }

    public int getOffset() {
        return pagination.offset;
    }

    private final class Pagination {
        private int count;
        private int offset;
    }
}
