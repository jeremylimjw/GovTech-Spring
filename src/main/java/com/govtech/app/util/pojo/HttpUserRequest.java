package com.govtech.app.util.pojo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Sort;

public class HttpUserRequest {
    private final List<String> sortables = List.of("name", "salary");

    private BigDecimal min = new BigDecimal(0);
    private BigDecimal max = new BigDecimal(4000);
    private int limit = Integer.MAX_VALUE;
    private int offset = 0;
    private String sort;

    public HttpUserRequest() {
    }

    public BigDecimal getMin() {
        return this.min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getMax() {
        return this.max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    // Checks if given column is sortable, else do not sort.
    // Illegal column names are ignored and no sorting will occur.
    public Sort getSorter() {
        if (this.sort == null || !this.sortables.contains(this.sort.toLowerCase())) {
            return Sort.unsorted();
        }
        return Sort.by(Sort.Direction.ASC, this.sort.toLowerCase());
    }

    @Override
    public String toString() {
        return "{" +
            " min='" + getMin() + "'" +
            ", max='" + getMax() + "'" +
            ", limit='" + getLimit() + "'" +
            ", offset='" + getOffset() + "'" +
            ", sort='" + getSort() + "'" +
            "}";
    }
    
}
