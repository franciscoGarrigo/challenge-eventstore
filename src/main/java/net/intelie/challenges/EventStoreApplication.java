package net.intelie.challenges;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
public class EventStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventStoreApplication.class, args);
    }
}
