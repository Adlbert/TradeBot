package com.trade.bot.service;

import com.binance.api.client.BinanceApiAsyncRestClient;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;

import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class ApiService {
    private static ResourceBundle apiBundle = ResourceBundle.getBundle("api");
    private static BinanceApiClientFactory apiClientFactory = BinanceApiClientFactory.newInstance(apiBundle.getString("API_KEY"), apiBundle.getString("API_SECRET"));
    protected static BinanceApiAsyncRestClient restAsyncClient = apiClientFactory.newAsyncRestClient();
    protected static BinanceApiRestClient restClient = apiClientFactory.newRestClient();
}
