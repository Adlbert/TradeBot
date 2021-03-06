package com.trade.bot.entity.currencies;

import com.trade.bot.entity.Statistic;

import java.util.ResourceBundle;

public class Currency {
    private String name;

    private Statistic askStatistic;

    private Statistic bidStatistic;

    public Currency(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    };

    public void setName(String name){
        this.name = name;
    };

    public Double getAskPriceTH(){
        return Double.parseDouble(ResourceBundle.getBundle("analyze").getString("AskPriceTH"));
    };
    public Double getAskQtyTH(){
        return Double.parseDouble(ResourceBundle.getBundle("analyze").getString("AskQtyTH"));

    };
    public Double getBidPriceTH(){
        return Double.parseDouble(ResourceBundle.getBundle("analyze").getString("BidPriceTH"));

    };
    public Double getBidQtyTH(){
        return Double.parseDouble(ResourceBundle.getBundle("analyze").getString("BidQtyTH"));

    };

    public Statistic getAskStatistic() {
        return askStatistic;
    }

    public void setAskStatistic(Statistic askStatistic) {
        this.askStatistic = askStatistic;
    }

    public Statistic getBidStatistic() {
        return bidStatistic;
    }

    public void setBidStatistic(Statistic bidStatistic) {
        this.bidStatistic = bidStatistic;
    }
}
