package com.trade.bot.interfaces.service;

import com.binance.api.client.domain.general.Asset;
import com.binance.api.client.domain.general.SymbolInfo;
import com.binance.api.client.domain.market.TickerPrice;
import com.trade.bot.entity.Arbitrage;

import java.util.List;

public interface IAbritrageService {
    void doArbitrageForAll(List<TickerPrice> allPrices, List<SymbolInfo> allSymbols) throws InterruptedException ;
    void doArbitrage(Arbitrage arbitrage);
}
