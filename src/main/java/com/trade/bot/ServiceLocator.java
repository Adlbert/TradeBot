package com.trade.bot;

import com.trade.bot.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceLocator {

    private static Cache cache = new Cache();

    public static Object getService(String serviceName) {

        Object service = cache.getService(serviceName);

        if (service != null) {
            return service;
        }

        InitialContext context = new InitialContext();
        Object service1 = (Object) context
                .lookup(serviceName);
        cache.addService(service1);
        return service1;
    }
}

class InitialContext {
    public Object lookup(String serviceName) {
        if (serviceName.equalsIgnoreCase("AnaylzeService")) {
            return new AnaylzeService();
        } else if (serviceName.equalsIgnoreCase("ArbitrageService")) {
            return new ArbitrageService();
        } else if (serviceName.equalsIgnoreCase("CurrencyService")) {
            return new CurrencyService();
        } else if (serviceName.equalsIgnoreCase("DatabaseService")) {
            return new DatabaseService();
        } else if (serviceName.equalsIgnoreCase("DockerSerivce")) {
            return new DockerSerivce();
        }
        return null;
    }
}

class Cache {
    private List<Object> services = new ArrayList<>();

    public Object getService(String serviceName) {
        Optional<Object> objectOptoinal =  services.stream().filter(s->s.getClass().getSimpleName().equals(serviceName)).findFirst();
        if(objectOptoinal.isPresent())
            return objectOptoinal.get();
        else
            return null;
    }

    public void addService(Object newService) {
        services.add(newService);
    }
}
