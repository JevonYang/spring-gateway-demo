package com.yang.gateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayApplicationTests   {

    WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();

    @Test
    public void contextLoads() throws InterruptedException {

    }

}
