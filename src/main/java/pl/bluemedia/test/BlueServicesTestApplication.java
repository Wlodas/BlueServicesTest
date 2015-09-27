package pl.bluemedia.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableJpaAuditing
@SpringBootApplication
public class BlueServicesTestApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BlueServicesTestApplication.class, args);
	}
	
}
