package gr.hua.it21774;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import gr.hua.it21774.config.JwtProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class It21774Application {

	public static void main(String[] args) {
		SpringApplication.run(It21774Application.class, args);
	}

}
