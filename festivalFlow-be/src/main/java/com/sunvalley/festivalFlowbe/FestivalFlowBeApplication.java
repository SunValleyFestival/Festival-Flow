package com.sunvalley.festivalFlowbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.sunvalley.festivalFlowbe.*")
@ComponentScan(basePackages = { "com.sunvalley.festivalFlowbe.*" })
@EntityScan("my.package.base.*")
public class FestivalFlowBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FestivalFlowBeApplication.class, args);
	}

}
