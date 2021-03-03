package com.trade.bot.service;

import java.io.File;

public class DockerSerivce {

    private static DockerSerivce instance;

    public static DockerSerivce getInstance() {
        if (instance == null) {
            instance = new DockerSerivce();
        }
        return instance;
    }

    public boolean isInsideDocker(){
        File file = new File("/.dockerenv");
       return file.exists();
    }
}
