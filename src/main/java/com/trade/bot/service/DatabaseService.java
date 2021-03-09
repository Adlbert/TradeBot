package com.trade.bot.service;

import com.binance.api.client.domain.general.Asset;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import com.trade.bot.interfaces.service.IDatabaseService;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseService implements IDatabaseService {

    public MongoClient getClient(boolean isInsideDocker){
        String connection;
        if (isInsideDocker)
            connection = "monogodb";
        else
            connection = "localhost";

        MongoCredential credential;
        credential = MongoCredential.createCredential(
                "admin",
                "admin",
                "admin".toCharArray());

        MongoClient client = new MongoClient( new ServerAddress(connection , 27017 ), Arrays.asList(credential));
        return client;
    }

    public MongoDatabase createDatabase(MongoClient client) {
        MongoDatabase database = client.getDatabase("tradebot_database");
        if (database.getCollection("Assets") == null)
            database.createCollection("Assets");
        if (database.getCollection("TickerPrices") == null)
            database.createCollection("TickerPrices");
        return database;
    }

    public List<Asset> LoadAllAssets(MongoDatabase database, Gson gson) {
        MongoCollection<Document> collection = database.getCollection("Assets");
        FindIterable<Document> assetDocs = collection.find();
        List<Asset> assets = new ArrayList<>();
        for (Document document : assetDocs){
            assets.add(gson.fromJson(document.toJson(), Asset.class));
        }
        return assets;

    }

    public void InsertAsset(MongoDatabase database, Gson gson, Asset asset){
        MongoCollection<Document> collection = database.getCollection("Assets");
        String jsonAsset = gson.toJson(asset);
        Document assetDoc = new Document(asset.getId(), jsonAsset);
        collection.insertOne(assetDoc);
    }

    public void InsertAssetList(MongoDatabase database, Gson gson, List<Asset> assets){
        MongoCollection<Document> collection = database.getCollection("Assets");
        List<Document> assetDocs = new ArrayList<>();
        for (Asset asset : assets){
            String jsonAsset = gson.toJson(asset);
            Document assetDoc = new Document(asset.getId(), jsonAsset);
            assetDocs.add(assetDoc);
        }
        collection.insertMany(assetDocs);
    }
}
