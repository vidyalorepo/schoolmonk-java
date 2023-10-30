package com.dcc.schoolmonk.elasticsearch.document;

public class FeesVo {
    private Double minFees;
    private Double maxFees;

    public FeesVo(Double minFees, Double maxFees) {
        this.minFees = minFees;
        this.maxFees = maxFees;
    }

    public FeesVo() {
    }

    public Double getMinFees() {
        return minFees;
    }

    public void setMinFees(Double minFees) {
        this.minFees = minFees;
    }

    public Double getMaxFees() {
        return maxFees;
    }

    public void setMaxFees(Double maxFees) {
        this.maxFees = maxFees;
    }

    public Boolean isEmpty() {
        return this.minFees == null && this.maxFees == null;
    }
}
