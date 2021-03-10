package com.trade.bot;

import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.trade.bot.model.AnaylzeCandleStick;
import com.trade.bot.model.Arbitrage;
import com.trade.bot.service.*;

public class TradeBot {

    //Services
    private AnaylzeService anaylzeService;
    private ArbitrageService arbitrageService;
    private CurrencyService currencyService;
    private DatabaseService databaseService;
    private DockerSerivce dockerSerivce;
    private ApiService apiService;

    //Helper
    private Database database;

    public static void main(String[] args) throws InterruptedException {

        TradeBot tradeBot = new TradeBot();
        tradeBot.load();
        tradeBot.start();

    }

    public void load(){
        System.out.println("Load Services ...");
        anaylzeService = (AnaylzeService) ServiceLocator.getService("AnaylzeService");
        arbitrageService = (ArbitrageService) ServiceLocator.getService("ArbitrageService");
        apiService = (ApiService) ServiceLocator.getService("ApiService");
        currencyService = (CurrencyService) ServiceLocator.getService("CurrencyService");
        databaseService = (DatabaseService) ServiceLocator.getService("DatabaseService");
        dockerSerivce = (DockerSerivce) ServiceLocator.getService("DockerSerivce");

        System.out.println("Load Database ...");
        database = new Database(databaseService, dockerSerivce);

    }

    public void start(){
        Arbitrage arbitrage = new Arbitrage(200,0, arbitrageService, apiService);
        //arbitrage.start();
        AnaylzeCandleStick anaylzeEthBtcCandleStick = new AnaylzeCandleStick("ETHBTC", CandlestickInterval.HOURLY, apiService, anaylzeService);

    }

    /*
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
    }*/
}
