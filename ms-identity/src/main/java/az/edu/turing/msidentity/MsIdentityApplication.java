package az.edu.turing.msidentity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MsIdentityApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsIdentityApplication.class, args);
	}

}
