package com.trade.bot;

import com.binance.api.client.domain.market.TickerPrice;
import com.trade.bot.entity.Arbitrage;
import com.trade.bot.entity.currencies.Bitcoin;
import com.trade.bot.entity.currencies.Currency;
import com.trade.bot.entity.currencies.Ethereum;
import com.trade.bot.entity.currencies.Euro;
import com.trade.bot.service.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TradeBot {

    private static TradeBot instance;

    public static TradeBot getInstance() {
        if(instance == null){
            instance = new TradeBot();
        }
        return instance;
    }

    public static void main(String[] args) throws InterruptedException {

/*        Bitcoin bitcoin = new Bitcoin();
        Ethereum ethereum = new Ethereum();
        Euro euro = new Euro();
        getInstance().doArbitrage(ethereum, euro, bitcoin,500, 200);*/
        System.out.println(DockerSerivce.getInstance().isInsideDocker());
        DatabaseService.getInstance().getClient();
        ArbitrageService.getInstance().doArbitrage();

        return;
    }

    private void doArbitrage(Currency currency1, Currency currency2, Currency currency3, int lots, double threshold){
        Arbitrage arbitrage = new Arbitrage(currency1, currency2, currency3, lots);
        while(true){
            ArbitrageService.getInstance().getExchangeRate(arbitrage);
            double profit = ArbitrageService.getInstance().getProfit(arbitrage);
            if(profit > threshold)
                System.out.println("NowBuy");
            System.out.println(profit);
        }
    }

    private void statisticExample(){
        Bitcoin bitcoin = new Bitcoin();
        Euro euro = new Euro();
        try {
            Future<TickerPrice> tickerPriceFuture = CurrencyService.getInstance().getEuroPriceAsync(bitcoin);
            TickerPrice tickerPrice = tickerPriceFuture.get();
            System.out.println(tickerPrice.getPrice()+"async");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        TickerPrice tickerPrice = CurrencyService.getInstance().getEuroPrice(bitcoin);
        System.out.println((tickerPrice.getPrice()+"sync"));

        AnaylzeService.getInstance().analyzeOrderBook(bitcoin, euro);
    }
}
