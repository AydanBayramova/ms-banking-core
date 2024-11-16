package az.edu.turing.msaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
@EnableFeignClients
public class MsAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsAccountApplication.class, args);
    }

}
