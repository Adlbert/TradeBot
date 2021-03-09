package com.trade.bot.interfaces.service;

import com.binance.api.client.domain.general.Asset;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.util.List;

public interface IDatabaseService {
    MongoClient getClient(boolean isInsideDocker);
    MongoDatabase createDatabase(MongoClient client);
    List<Asset> LoadAllAssets(MongoDatabase mongoDatabase, Gson gson);
}
