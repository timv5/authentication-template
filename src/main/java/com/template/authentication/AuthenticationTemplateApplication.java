package com.template.authentication;

import com.template.authentication.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.template"})
@EnableJpaRepositories(basePackages = {"com.template"})
@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication(scanBasePackages = {"com.template"})
public class AuthenticationTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationTemplateApplication.class, args);
	}

}
