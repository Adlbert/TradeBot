package com.trade.bot.interfaces.service;

import com.binance.api.client.domain.general.Asset;
import com.binance.api.client.domain.market.TickerPrice;
import com.trade.bot.entity.currencies.Currency;
import org.javatuples.Pair;

import java.util.List;
import java.util.concurrent.Future;

public interface ICurrencyService {
    Future<TickerPrice> getEuroPriceAsync(Currency currency)  throws InterruptedException;
    TickerPrice getEuroPrice(Currency currency);
    Future<TickerPrice> getPriceAsync(Currency currency1, Currency currency2);
    Future<Double> getPriceTradeBotoubleAsync(Currency currency1, Currency currency2);
    TickerPrice getPrice(Currency currency1, Currency currency2);
    Double getPriceTradeBotouble(Currency currency1, Currency currency2);
    Pair<Asset,Asset> getAssetFromSymbol(List<Asset> allAssets, String symbol);
}
