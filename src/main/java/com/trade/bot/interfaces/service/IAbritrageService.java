package com.trade.bot.interfaces.service;

import com.trade.bot.entity.Arbitrage;

public interface IAbritrageService {
    void doArbitrageForAll() throws InterruptedException ;
    void doArbitrage(Arbitrage arbitrage);
}
