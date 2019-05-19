package com.sap.slh.tax.calculation;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@ComponentScan("com.sap.slh.tax")
@SpringBootApplication
@EnableAsync
@EnableCaching
@EnableRabbit
public class TaxCalculationWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxCalculationWebApplication.class, args);
	}

}
