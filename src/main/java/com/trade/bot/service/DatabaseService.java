package com.trade.bot.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class DatabaseService {
    public MongoClient getClient(){
        MongoClient client = MongoClients.create("<<MongoDB URI>>");
        return client;
    }
}
