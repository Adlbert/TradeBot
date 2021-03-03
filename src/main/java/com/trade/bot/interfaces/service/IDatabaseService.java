package com.trade.bot.interfaces.service;

import com.mongodb.client.MongoClient;

public interface IDatabaseService {
    MongoClient getClient();
}
