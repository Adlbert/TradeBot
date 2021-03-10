package com.trade.bot.interfaces.service;

import com.binance.api.client.domain.market.OrderBook;
import com.trade.bot.entity.currencies.Currency;

public interface IAnalyzeService {
    void analyzeOrderBook(OrderBook orderBook, Currency currencyFrom, Currency currencyTo);
}
