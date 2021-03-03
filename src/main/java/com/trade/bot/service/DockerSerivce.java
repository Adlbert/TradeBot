package com.trade.bot.service;

import com.trade.bot.interfaces.service.IDockerService;

import java.io.File;

public class DockerSerivce implements IDockerService {

    public boolean isInsideDocker(){
        File file = new File("/.dockerenv");
       return file.exists();
    }
}
