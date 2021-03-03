package com.trade.bot.interfaces.service;

import com.trade.bot.entity.currencies.Currency;

public interface IAnalyzeService {
    void analyzeOrderBook(Currency currencyFrom, Currency currencyTo);
}
