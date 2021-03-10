package com.trade.bot.service;

import com.binance.api.client.domain.general.SymbolInfo;
import com.binance.api.client.domain.market.TickerPrice;
import com.trade.bot.model.Arbitrage;
import com.trade.bot.interfaces.service.IAbritrageService;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;

public class ArbitrageService implements IAbritrageService {

    public void doArbitrage(String Symbol, double lots,double threshold, List<TickerPrice> allPrices, List<SymbolInfo> allSymbols){
        throw new NotImplementedException("This method is not implemented yet.");
    }

    public void doArbitrageForAll(double lots,double threshold, List<TickerPrice> allPrices, List<SymbolInfo> allSymbols) throws InterruptedException {

        int count = 0;
        for (int i = 0; i < allPrices.size(); i++) {
            double price;
            String  priceSymbol = "BTC";

            TickerPrice tickerPrice1 = allPrices.get(i);
            SymbolInfo info1 = allSymbols.stream().filter(si->si.getSymbol().equals(tickerPrice1.getSymbol())).findFirst().get();
            double price1 = Double.parseDouble(tickerPrice1.getPrice());
            if(info1.getBaseAsset().equals(priceSymbol))
                continue;
            TickerPrice tickerPrice;
            try {
                tickerPrice = allPrices.stream().filter(tp -> tp.getSymbol().equals(info1.getBaseAsset() + "BTC")).findFirst().get();
            }catch(Exception ex){
                priceSymbol = "BNB";
                try {
                    tickerPrice = allPrices.stream().filter(tp -> tp.getSymbol().equals(info1.getBaseAsset() + "BNB")).findFirst().get();
                }catch(Exception ex1){
                    System.out.println(info1.getBaseAsset());
                    continue;
                }
            }
            price = Double.parseDouble(tickerPrice.getPrice());
            lots = price / lots;

            for (int j = 0; j < allPrices.size(); j++) {
                if (i == j)
                    continue;
                TickerPrice tickerPrice2 = allPrices.get(j);
                SymbolInfo info2 = allSymbols.stream().filter(si->si.getSymbol().equals(tickerPrice2.getSymbol())).findFirst().get();

                double price2 = Double.parseDouble(tickerPrice2.getPrice());
                if (info1.getQuoteAsset().equals(info2.getBaseAsset())) {
                    for (int k = 0; k < allPrices.size(); k++) {
                        if (j == k)
                            continue;
                        TickerPrice tickerPrice3 = allPrices.get(k);
                        SymbolInfo info3 = allSymbols.stream().filter(si -> si.getSymbol().equals(tickerPrice3.getSymbol())).findFirst().get();
                        if (info2.getQuoteAsset().equals(info3.getBaseAsset())&& info3.getQuoteAsset().equals(info1.getBaseAsset())) {
                            double price3 = Double.parseDouble(tickerPrice3.getPrice());

                            double profit = getProfit(price1, price2, price3, lots);
                            if (profit* price > threshold* price) {
                                System.out.println("NowBuy");
                                System.out.println(info1.getQuoteAsset() + " " + info2.getQuoteAsset() + " " + info3.getQuoteAsset());
                                System.out.println(tickerPrice1.getSymbol() + " " + price1);
                                System.out.println(tickerPrice2.getSymbol() + " " + price2);
                                System.out.println(tickerPrice3.getSymbol() + " " + price3);
                                System.out.println(profit * price + priceSymbol);
                                count += 1;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Found " + count + " out of " + (allPrices.size() * allPrices.size() - allPrices.size()));
    }

    private double getProfit(Double price1,  Double price2, Double price3, double lots){
        double ex1 = lots/ price1;
        double ex2 = ex1 / price2;
        double ex3 = ex2 / price3;
        return ex3 - lots;
    }

}
