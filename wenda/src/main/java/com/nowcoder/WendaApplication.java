package com.nowcoder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication

public class WendaApplication {


	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(WendaApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(WendaApplication.class, args);
	}
}
