package com.example.accessingdatapostgressql;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
//----------------------------------------------------


@Configuration
@SpringBootApplication
public class AccessingDataPostGresSqlApplication //implements CommandLineRunner
{
	
	   @Value("${spring.datasource.url}")
	    private String mySecretProperty;
	
	public static void main(String[] args) {
		SpringApplication.run(AccessingDataPostGresSqlApplication.class, args);
		
	}
/*
	 @Override
	    public void run(String... args) {
	        System.out.println("property your-property-name value is: " + mySecretProperty);
	    }
	*/
}
