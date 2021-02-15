package com.trade.bot;

import com.binance.api.client.BinanceApiClientFactory;

import java.util.ResourceBundle;

public class TradeBot {
    public static void main(String[] args){

        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(
                ResourceBundle.getBundle("api").getString("API_KEY"),
                ResourceBundle.getBundle("api").getString("API_SECRET")
        );
    }
}
