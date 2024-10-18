package com.MyOrganization.MyOrganization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class MyOrganizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyOrganizationApplication.class, args);
	}

}
