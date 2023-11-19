package com.cbfacademy.apiassessment;

import com.cbfacademy.apiassessment.config.RestTemplateConfig;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Import(RestTemplateConfig.class)
@ComponentScan(basePackages ="com.cbfacademy.apiassessment")
public class App {
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();

	}
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}


}
