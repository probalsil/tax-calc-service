package com.sap.slh.tax.calculation.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sap.slh.tax.calculation.service.TaxCalculationListener;

import RabbitConfigurationTemplate.RabbitConfigurationTemplate.BindingConfiguration;
import RabbitConfigurationTemplate.RabbitConfigurationTemplate.RabbitMQConfig;

@EnableRabbit
@Configuration
@EnableAutoConfiguration(exclude = { org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration.class })
public class ApplicationBeanConfig {


	@Value("${vcap.services.rabbitmq.credentials.hostname}")
	private String rabbitmqHost;

	@Value("${vcap.services.rabbitmq.credentials.port}")
	private int rabbitmqPort;

	@Value("${vcap.services.rabbitmq.credentials.username}")
	private String rabbitmqUsername;

	@Value("${vcap.services.rabbitmq.credentials.password}")
	private String rabbitmqPassword;

	@Value("${rabbitmq.calc.queue}")
	private String queue;

	@Value("${rabbitmq.calc.exchange}")
	private String exchange;

	@Value("${rabbitmq.calc.routingKey}")
	private String routingKey;
	
	@Bean
	public TaxCalculationListener taxCalculationService() {
		return new TaxCalculationListener();
	}

	@Bean
	public List<BindingConfiguration> bindingConfigurationList() {
		List<BindingConfiguration> bindingConfigurationList = new ArrayList<BindingConfiguration>();
		bindingConfigurationList.add(new BindingConfiguration(queue, exchange, routingKey));
		return bindingConfigurationList;

	}
	
	@Bean
	public SimpleRabbitListenerContainerFactory remoteContainerFactory(
			List<BindingConfiguration> bindingConfigurationList) {
		RabbitMQConfig rabbitMQConfig = new RabbitMQConfig(rabbitmqHost, rabbitmqPort, rabbitmqUsername, rabbitmqPassword);
		ConnectionFactory factory = rabbitMQConfig.createConnectionFactory();
		rabbitMQConfig.createRabbitTemplate(factory);
		rabbitMQConfig.createBindings(bindingConfigurationList, factory);
		SimpleRabbitListenerContainerFactory containerFactory = rabbitMQConfig.createContainerFactory(factory);
		return containerFactory;

	}
	
}
