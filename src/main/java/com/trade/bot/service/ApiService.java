package com.trade.bot.service;

import com.binance.api.client.BinanceApiAsyncRestClient;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;

import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApiService {

    private BinanceApiAsyncRestClient restAsyncClient;
    private BinanceApiRestClient restClient;


    public ApiService(){
        ResourceBundle apiBundle = ResourceBundle.getBundle("api");
        BinanceApiClientFactory apiClientFactory = BinanceApiClientFactory.newInstance(apiBundle.getString("API_KEY"), apiBundle.getString("API_SECRET"));
        restAsyncClient = apiClientFactory.newAsyncRestClient();
        restClient = apiClientFactory.newRestClient();
    }

    public BinanceApiAsyncRestClient getRestAsyncClient() {
        return restAsyncClient;
    }

    public BinanceApiRestClient getRestClient() {
        return restClient;
    }
}
