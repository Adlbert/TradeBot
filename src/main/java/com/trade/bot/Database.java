package com.trade.bot;

import com.binance.api.client.domain.general.Asset;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.trade.bot.service.DatabaseService;
import com.trade.bot.service.DockerSerivce;

import java.util.List;

public class Database {
    private DatabaseService dbservice;
    private DockerSerivce dockerSerivce;
    private MongoClient client;
    private MongoDatabase mongoDatabase;
    private Gson gson;

    public Database(DatabaseService dbservice, DockerSerivce dockerSerivce) {
        this.dbservice = dbservice;
        this.dockerSerivce = dockerSerivce;
        this.client = dbservice.getClient(dockerSerivce.isInsideDocker());
        this.mongoDatabase = dbservice.createDatabase(client);
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void insertAsset(Asset asset){
        dbservice.InsertAsset(mongoDatabase, gson, asset);
    }

    public void insertAssetList(List<Asset> assets){
        dbservice.InsertAssetList(mongoDatabase, gson, assets);
    }

    public List<Asset> LoadAllAssets() {
        return dbservice.LoadAllAssets(mongoDatabase, gson);
    }
}
