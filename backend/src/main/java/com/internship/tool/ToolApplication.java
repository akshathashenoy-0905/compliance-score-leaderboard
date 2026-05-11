package com.internship.tool;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@EnableAspectJAutoProxy
public class ToolApplication {
	public static void main(String[] args) {
		SpringApplication.run(ToolApplication.class, args);
	}
}