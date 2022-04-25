package SEP4Data.air4you;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"SEP4Data.air4you.user", "controllers"})
@EnableJpaRepositories
@Configuration
@EnableAutoConfiguration
//@ComponentScans(value = { @ComponentScan("SEP4Data.air4you.repository"),
//						  @ComponentScan("SEP4Data.air4you.controllers"),
//		@ComponentScan("SEP4Data.air4you.controllers")
//})
public class Air4youApplication {

	public static void main(String[] args) {
		SpringApplication.run(Air4youApplication.class, args);
	}

}
