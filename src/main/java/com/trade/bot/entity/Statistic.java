package com.trade.bot.entity;

public class Statistic {
    private Double minPrice;
    private Double maxPrice;
    private Double avgPrice;
    private Double minQty;
    private Double maxQty;
    private Double avgQty;

    public Statistic() {
        minPrice = Double.MAX_VALUE;
        maxPrice = 0.0;
        avgPrice = 0.0;
        minQty = Double.MAX_VALUE;
        maxQty = 0.0;
        avgQty = 0.0;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Double getMinQty() {
        return minQty;
    }

    public void setMinQty(Double minQty) {
        this.minQty = minQty;
    }

    public Double getMaxQty() {
        return maxQty;
    }

    public void setMaxQty(Double maxQty) {
        this.maxQty = maxQty;
    }

    public Double getAvgQty() {
        return avgQty;
    }

    public void setAvgQty(Double avgQty) {
        this.avgQty = avgQty;
    }
}
