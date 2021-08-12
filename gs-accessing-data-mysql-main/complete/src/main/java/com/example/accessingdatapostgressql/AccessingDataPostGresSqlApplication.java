package com.example.accessingdatapostgressql;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//----------------------------------------------------
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;



@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class AccessingDataPostGresSqlApplication extends SpringBootServletInitializer//implements CommandLineRunner
{
	
	
	public static void main(String[] args) {
		SpringApplication.run(AccessingDataPostGresSqlApplication.class, args);
		
	}
	@Override  
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)   
	{  
	return application.sources(AccessingDataPostGresSqlApplication.class);  
	}   
	
	
/*
	 @Override
	    public void run(String... args) {
	        System.out.println("property your-property-name value is: " + mySecretProperty);
	    }
	*/
	 
}
