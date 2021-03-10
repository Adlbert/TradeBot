package com.trade.bot.interfaces.service;

import com.binance.api.client.domain.general.SymbolInfo;
import com.binance.api.client.domain.market.TickerPrice;
import com.trade.bot.model.Arbitrage;

import java.util.List;

public interface IAbritrageService {
    void doArbitrageForAll(double lots,double threshold,List<TickerPrice> allPrices, List<SymbolInfo> allSymbols) throws InterruptedException ;
    void doArbitrage(String Symbol, double lots,double threshold, List<TickerPrice> allPrices, List<SymbolInfo> allSymbols);
}
