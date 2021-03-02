package com.trade.bot.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class DatabaseService {

    private static DatabaseService instance;

    public static DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }

    public MongoClient getClient(){
        MongoClient client = MongoClients.create("mongodb://monogodb:27017");
        return client;
    }
}
