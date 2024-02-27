package com.sunvalley.festivalFlowbe;

import com.sunvalley.festivalFlowbe.utility.EmailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.sunvalley.festivalFlowbe.*")
@ComponentScan(basePackages = { "com.sunvalley.festivalFlowbe.*" })
@EntityScan("com.sunvalley.festivalFlowbe.*")
public class FestivalFlowBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FestivalFlowBeApplication.class, args);
	}

}
