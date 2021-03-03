package com.trade.bot.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.trade.bot.interfaces.service.IDatabaseService;

public class DatabaseService implements IDatabaseService {

    public MongoClient getClient(){
        MongoClient client = MongoClients.create("mongodb://monogodb:27017");
        return client;
    }
}
