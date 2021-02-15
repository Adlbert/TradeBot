package com.trade.bot;

import com.binance.api.client.domain.market.TickerPrice;
import com.trade.bot.entity.currencies.Bitcoin;
import com.trade.bot.entity.currencies.Euro;
import com.trade.bot.service.AnaylzeService;
import com.trade.bot.service.CurrencyService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TradeBot {
    public static void main(String[] args){

        Bitcoin bitcoin = new Bitcoin();
        Euro euro = new Euro();
        try {
            Future<TickerPrice> tickerPriceFuture = CurrencyService.getEuroPriceAsync(bitcoin);
            TickerPrice tickerPrice = tickerPriceFuture.get();
            System.out.println(tickerPrice.getPrice()+"async");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        TickerPrice tickerPrice = CurrencyService.getEuroPrice(bitcoin);
        System.out.println((tickerPrice.getPrice()+"sync"));

        AnaylzeService.getInstance().analyzeOrderBook(bitcoin, euro);

        return;
    }
}
