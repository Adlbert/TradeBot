package com.trade.bot.model;

import com.binance.api.client.domain.general.ExchangeInfo;
import com.binance.api.client.domain.market.TickerPrice;
import com.trade.bot.service.ApiService;
import com.trade.bot.service.ArbitrageService;

import java.util.List;

public class Arbitrage {

    private ArbitrageService arbitrageService;
    private ApiService apiService;

    private double lots;
    private double threshold;

    public Arbitrage(double lots, double threshold, ArbitrageService arbitrageService, ApiService apiService){
        this.lots = lots;
        this.threshold = threshold;
        this.arbitrageService = arbitrageService;
        this.apiService = apiService;
    }

    public void start(){
        List<TickerPrice> allPrices = apiService.getRestClient().getAllPrices();
        ExchangeInfo info = apiService.getRestClient().getExchangeInfo();
        try {
            arbitrageService.doArbitrageForAll(lots, threshold, allPrices, info.getSymbols());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public double getLots() {
        return lots;
    }

    public void setLots(double lots) {
        this.lots = lots;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
}
