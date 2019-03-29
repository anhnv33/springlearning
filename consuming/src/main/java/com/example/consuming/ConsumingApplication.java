package com.example.consuming;

import com.example.consuming.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ConsumingApplication {

    private static final Logger log = LoggerFactory.getLogger(ConsumingApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(ConsumingApplication.class);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            Quote quote = restTemplate.getForObject(
                    "http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
            ResponseEntity entity = restTemplate.getForEntity("http://gturnquist-quoters.cfapps.io/api/random", Object.class);
            log.info(quote.toString());
            log.info(entity.getBody().toString());
        };
    }

}
