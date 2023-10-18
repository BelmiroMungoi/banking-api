package com.bbm.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BbmBankApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BbmBankApiApplication.class, args);
	}

}
