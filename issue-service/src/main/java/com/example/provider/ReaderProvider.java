package com.example.provider;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ReaderProvider {
    private final WebClient webClient;
    private final EurekaClient eurekaClient;


    public ReaderProvider(EurekaClient eurekaClient) {
        webClient = WebClient.builder().build();
        this.eurekaClient = eurekaClient;
    }

    public ReaderProvider.ReaderResponse getReader(long id){

        ReaderProvider.ReaderResponse reader = webClient.get()
//                .uri("http://localhost:8280/api/reader/" + id)
//                .uri(getReaderServiceIP() + "/api/reader/" + id)
                .uri(new Config(eurekaClient).getServiceIP("READER-SERVICE") + "/api/reader/" + id)
                .retrieve()
                .bodyToMono(ReaderProvider.ReaderResponse.class)
                .block();
        return reader;
    }

    // ToDo
//    POST, DELETE, PUT


    private String getReaderServiceIP(){
        Application readerService = eurekaClient.getApplication("READER-SERVICE");
        List<InstanceInfo> instances = readerService.getInstances();

        int randomIndex = ThreadLocalRandom.current().nextInt(instances.size());
        InstanceInfo randomInstance = instances.get(randomIndex);
        return "http://" + randomInstance.getIPAddr() + ":" + randomInstance.getPort();
    }

    @Data
    public static class ReaderResponse{
        private long id;
        private String name;
    }
}
