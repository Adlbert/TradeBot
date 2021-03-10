package com.trade.bot.service;

import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.account.request.AllOrdersRequest;
import com.binance.api.client.domain.account.request.OrderRequest;
import com.binance.api.client.domain.market.OrderBook;
import com.binance.api.client.domain.market.OrderBookEntry;
import com.trade.bot.entity.Statistic;
import com.trade.bot.entity.currencies.Currency;
import com.trade.bot.interfaces.service.IAnalyzeService;

import java.util.ArrayList;
import java.util.List;

public class AnaylzeService implements IAnalyzeService {

    private void getStatistics(OrderBook orderBook, Statistic statistic){
        Double avgQty  = 0.0;
        Double avgPrice = 0.0;

        for(OrderBookEntry order : orderBook.getAsks()){
            Double price = Double.parseDouble(order.getPrice());
            Double qty = Double.parseDouble(order.getQty());
            avgQty += qty;
            avgPrice += price;
            if(price < statistic.getMinPrice())
                statistic.setMinPrice(price);
            if(price > statistic.getMaxPrice())
                statistic.setMaxPrice(price);
            if(qty < statistic.getMinQty())
                statistic.setMinQty(qty);
            if(qty > statistic.getMaxQty())
                statistic.setMaxQty(qty);
        }
        avgQty /= orderBook.getAsks().size();
        avgPrice /= orderBook.getAsks().size();
        statistic.setAvgQty(avgQty);
        statistic.setAvgPrice(avgPrice);
    }

    private void getAskStatistics(Currency currency, OrderBook orderBook){
        Statistic askStatistic = new Statistic();
        getStatistics(orderBook, askStatistic);
        currency.setAskStatistic(askStatistic);
    }

    private void getBidStatistics(Currency currency, OrderBook orderBook){
        Statistic bidStatistic = new Statistic();
        getStatistics(orderBook, bidStatistic);
        currency.setBidStatistic(bidStatistic);
    }

    public void analyzeOrderBook(OrderBook orderBook, Currency currencyFrom, Currency currencyTo){
        List<OrderBookEntry> interestingBids = new ArrayList<>();
        List<OrderBookEntry> interestingAsks = new ArrayList<>();
        //legal range is '5, 10, 20, 50, 100, 500, 1000, 5000'
        getBidStatistics(currencyTo, orderBook);
        getAskStatistics(currencyTo, orderBook);


        for(OrderBookEntry order : orderBook.getBids()){
            Double price = Double.parseDouble(order.getPrice());
            Double qty = Double.parseDouble(order.getQty());

            //Agree to find something if price is higher than 70% of max price and Quantity is higher than 90% of max Quantity
            if(price > currencyTo.getBidPriceTH()* currencyTo.getBidStatistic().getMaxPrice() && qty > currencyTo.getBidQtyTH()* currencyTo.getBidStatistic().getMaxQty()){
                interestingBids.add(order);
            }
        }

        for(OrderBookEntry order : orderBook.getAsks()){
            Double price = Double.parseDouble(order.getPrice());
            Double qty = Double.parseDouble(order.getQty());

            //Agree to find something if price is higher than 70% of max price and Quantity is higher than 90% of max Quantity
            if(price > currencyTo.getAskPriceTH()* currencyTo.getAskStatistic().getMaxPrice() && qty > currencyTo.getAskQtyTH()* currencyTo.getAskStatistic().getMaxQty()){
                interestingAsks.add(order);
            }
        }

        System.out.println(interestingBids.size());
        System.out.println(interestingAsks.size());
    }
}
