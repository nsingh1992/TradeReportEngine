package com.Vanguard.TradeReportEngine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@ComponentScan(basePackages = {"com.Vanguard.TradeReportEngine"})
@PropertySource("classpath:application.properties")
public class TradeReportEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeReportEngineApplication.class, args);
	}

}
