package com.trade.bot.model;

import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.trade.bot.service.AnaylzeService;
import com.trade.bot.service.ApiService;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AnaylzeCandleStick {

    private ApiService apiService;
    private Map<Long, Candlestick> candlesticksCache;

    private String symbol;

    public AnaylzeCandleStick(String symbol, CandlestickInterval interval, ApiService apiService, AnaylzeService anaylzeService){
        this.apiService = apiService;
        this.symbol = symbol;
        initializeCandlestickCache(symbol, interval);
        startCandlestickEventStreaming(symbol, interval);
    }

    /**
     * Initializes the candlestick cache by using the REST API.
     */
    private void initializeCandlestickCache(String symbol, CandlestickInterval interval) {
        List<Candlestick> candlestickBars = apiService.getRestClient().getCandlestickBars(symbol.toUpperCase(), interval);

        this.candlesticksCache = new TreeMap<>();
        for (Candlestick candlestickBar : candlestickBars) {
            candlesticksCache.put(candlestickBar.getOpenTime(), candlestickBar);
        }
    }

    /**
     * Begins streaming of depth events.
     */
    private void startCandlestickEventStreaming(String symbol, CandlestickInterval interval) {
        apiService.getWebSocketClient().onCandlestickEvent(symbol.toLowerCase(), interval, response -> {
            Long openTime = response.getOpenTime();
            Candlestick updateCandlestick = candlesticksCache.get(openTime);
            if (updateCandlestick == null) {
                // new candlestick
                updateCandlestick = new Candlestick();
            }
            // update candlestick with the stream data
            updateCandlestick.setOpenTime(response.getOpenTime());
            updateCandlestick.setOpen(response.getOpen());
            updateCandlestick.setLow(response.getLow());
            updateCandlestick.setHigh(response.getHigh());
            updateCandlestick.setClose(response.getClose());
            updateCandlestick.setCloseTime(response.getCloseTime());
            updateCandlestick.setVolume(response.getVolume());
            updateCandlestick.setNumberOfTrades(response.getNumberOfTrades());
            updateCandlestick.setQuoteAssetVolume(response.getQuoteAssetVolume());
            updateCandlestick.setTakerBuyQuoteAssetVolume(response.getTakerBuyQuoteAssetVolume());
            updateCandlestick.setTakerBuyBaseAssetVolume(response.getTakerBuyQuoteAssetVolume());

            // Store the updated candlestick in the cache
            candlesticksCache.put(openTime, updateCandlestick);
            System.out.println(updateCandlestick);
        });
    }

    public void analyze1(){
        List<Candlestick> candlestickList = apiService.getRestClient().getCandlestickBars(symbol, CandlestickInterval.HOURLY);
        for (Candlestick candlestick : candlestickList){
        }
    }

    public String getSymbol() {
        return symbol;
    }
}
