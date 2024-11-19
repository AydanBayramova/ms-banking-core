package az.edu.turing.mscurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients(basePackages = "az.edu.turing.mscurrency")
@EnableScheduling
@EnableCaching
@EnableEurekaServer
public class MsCurrencyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCurrencyApplication.class, args);
    }

}
