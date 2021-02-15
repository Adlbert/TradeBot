package com.trade.bot.service;

import com.binance.api.client.BinanceApiCallback;
import com.binance.api.client.domain.market.TickerPrice;
import com.trade.bot.entity.currencies.Currency;

import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class CurrencyService extends ApiService {

    private static ResourceBundle currencyBundle = ResourceBundle.getBundle("currency");

    public static Future<TickerPrice> getEuroPriceAsync(Currency currency)  throws InterruptedException {
        BinanceApiCallback<TickerPrice> callback;
        CompletableFuture<TickerPrice> tickerPriceCompletableFuture = new CompletableFuture<>();
        restAsyncClient.getPrice(currency.getName() + currencyBundle.getString("Euro"), (response)->{
            tickerPriceCompletableFuture.complete(response);});
        return tickerPriceCompletableFuture;
    }

    public static TickerPrice getEuroPrice(Currency currency){
        return restClient.getPrice(currency.getName() + currencyBundle.getString("Euro"));
    }
}
