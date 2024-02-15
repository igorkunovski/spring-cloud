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
public class BookProvider {

    private final WebClient webClient;
    private final EurekaClient eurekaClient;

    public BookProvider(EurekaClient eurekaClient) {
        webClient = WebClient.builder().build();
        this.eurekaClient=eurekaClient;
    }

    public BookResponse getBook(long id){

       BookResponse book = webClient.get()
//                 .uri("http://localhost:8180/api/book/" + id)
//                 .uri(getBookServiceIP() + "/api/book/" + id)
                 .uri(new Config(eurekaClient).getServiceIP("BOOK-SERVICE") + "/api/book/" + id)
                 .retrieve()
                 .bodyToMono(BookResponse.class)
                 .block();
       return book;
    }


    // ToDo
//    POST, DELETE, PUT

    private String getBookServiceIP(){
        Application readerService = eurekaClient.getApplication("BOOK-SERVICE");
        List<InstanceInfo> instances = readerService.getInstances();

        int randomIndex = ThreadLocalRandom.current().nextInt(instances.size());
        InstanceInfo randomInstance = instances.get(randomIndex);
        return "http://" + randomInstance.getIPAddr() + ":" + randomInstance.getPort();
    }

    @Data
    public static class BookResponse{
        private long id;
        private String title;
        private String author;

    }
}
