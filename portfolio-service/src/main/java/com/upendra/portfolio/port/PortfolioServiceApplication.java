package com.upendra.portfolio.port;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PortfolioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioServiceApplication.class, args);
	}

}
