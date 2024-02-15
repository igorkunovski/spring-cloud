package com.example.provider;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Config {

    private final EurekaClient eurekaClient;

    public Config(EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    public String getServiceIP(String serviceName){

        Application readerService = eurekaClient.getApplication(serviceName);
        List<InstanceInfo> instances = readerService.getInstances();

        int randomIndex = ThreadLocalRandom.current().nextInt(instances.size());
        InstanceInfo randomInstance = instances.get(randomIndex);
        return "http://" + randomInstance.getIPAddr() + ":" + randomInstance.getPort();
    }
}
