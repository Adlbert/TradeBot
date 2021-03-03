package com.trade.bot.service;

import com.binance.api.client.BinanceApiCallback;
import com.binance.api.client.domain.general.Asset;
import com.binance.api.client.domain.market.TickerPrice;
import com.binance.api.client.exception.BinanceApiException;
import com.trade.bot.entity.Arbitrage;
import com.trade.bot.entity.currencies.Bitcoin;
import com.trade.bot.entity.currencies.Currency;
import com.trade.bot.interfaces.service.IAbritrageService;
import org.javatuples.Pair;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ArbitrageService extends ApiService implements IAbritrageService {

    public void doArbitrage(Arbitrage arbitrage){
        getProfit(arbitrage);
    }

    public void doArbitrageForAll() throws InterruptedException {
        List<TickerPrice> allPrices = restClient.getAllPrices();
        BinanceApiCallback<List<Asset>> callback;
        CompletableFuture<List<Asset>> allAssetsCompletableFuture = new CompletableFuture<>();
        restAsyncClient.getAllAssets(allAssets ->
                allAssetsCompletableFuture.complete(allAssets));
        while(!allAssetsCompletableFuture.isDone()){
            System.out.println("Waiting");
            Thread.sleep(4000);
        }
        double lots = 2000;
        double threshold = 200;
        int count = 0;
        for (int i = 0; i < allPrices.size(); i++) {
            TickerPrice tickerPrice1 = allPrices.get(i);
            double price;
            double price1 = Double.parseDouble(tickerPrice1.getPrice());
            //CurrencyService.getInstance().getAssetFromSymbol(allAssets, tickerPrice1.getSymbol());
            Currency c1 = new Currency(tickerPrice1.getSymbol().substring(0, 3));
            Currency c2 = new Currency(tickerPrice1.getSymbol().substring(3, tickerPrice1.getSymbol().length()));
            Optional<TickerPrice> optionalTickerPrice = allPrices.stream().filter(tp -> tp.getSymbol().equals(c1.getName() + "BTC")).findFirst();
            if (optionalTickerPrice.isPresent()) {
                TickerPrice tp = optionalTickerPrice.get();
                price = Double.parseDouble(tp.getPrice());
                lots = price / lots;
            } else {
                continue;
/*                try {
                    price = CurrencyService.getInstance().getPriceAsDouble(c1, new Bitcoin());
                } catch (BinanceApiException ex) {
                    continue;
                }*/
            }

            for (int j = 0; j < allPrices.size(); j++) {
                if (i == j)
                    continue;
                TickerPrice tickerPrice2 = allPrices.get(j);
                double price2 = Double.parseDouble(tickerPrice2.getPrice());
                if (tickerPrice2.getSymbol().contains(c2.getName())) {
                    Currency c3 = new Currency(tickerPrice2.getSymbol().replace(c2.getName(), ""));
                    if (tickerPrice2.getSymbol().equals(c3.getName() + c2.getName())) {
                        continue;
/*                        price2 = 1 / price2;
                        try {
                            tickerPrice2.setSymbol(tickerPrice2.getSymbol().substring(3, 6) + tickerPrice2.getSymbol().substring(0, 3));
                        } catch (StringIndexOutOfBoundsException ex) {
                            System.out.println(tickerPrice2);
                            continue;
                        }*/
                    }
                    Double price3;
                    Optional<TickerPrice> optionalTickerPrice3 = allPrices.stream().filter(tp -> tp.getSymbol().equals(c3.getName() + c1.getName())).findFirst();
                    if (optionalTickerPrice3.isPresent()) {
                        price3 = Double.parseDouble(optionalTickerPrice3.get().getPrice());
                        if (optionalTickerPrice3.get().getSymbol().equals(c1.getName() + c3.getName())) {
                            continue;
/*                            price3 = 1 / price3;
                            optionalTickerPrice3.get().setSymbol(optionalTickerPrice3.get().getSymbol().substring(3, 6) + optionalTickerPrice3.get().getSymbol().substring(0, 3));*/
                        }
                    } else {
                        continue;
/*                        try {
                            price3 = CurrencyService.getInstance().getPriceAsDouble(c3, c1);
                        } catch (BinanceApiException ex) {
                            continue;
                        }*/
                    }
                    Arbitrage arbitrage = new Arbitrage(c1, c2, c3, price1, price2, price3, lots);
                    double profit = getProfit(arbitrage);
                    if (profit > threshold) {
                        System.out.println("NowBuy");
                        System.out.println(c1.getName() + " " + c2.getName() + " " + c3.getName());
                        System.out.println(tickerPrice1.getSymbol() + " " + price1);
                        System.out.println(tickerPrice2.getSymbol() + " " + price2);
                        if (optionalTickerPrice3.isPresent()) {
                            System.out.println(optionalTickerPrice3.get().getSymbol() + " " + price3);
                        }
                        System.out.println(profit * price + "BTC");
                        count += 1;
                    }
                }
            }
        }
        System.out.println("Found " + count + " out of " + (allPrices.size() * allPrices.size() - allPrices.size()));
    }

    private double getProfit(Arbitrage arbitrage) {
        List<Pair<Pair, Double>> orderedCurrencies = arbitrage.getOrderedCurrencyList();
        double ex1 = arbitrage.getLots() / orderedCurrencies.get(0).getValue1();
        double ex2 = ex1 / orderedCurrencies.get(1).getValue1();
        double ex3 = ex2 / orderedCurrencies.get(0).getValue1();
        return ex3 - arbitrage.getLots();
    }
}
