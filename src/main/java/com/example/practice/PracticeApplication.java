package com.example.practice;

import com.example.practice.controller.HomeController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class PracticeApplication {

	public static void main(String[] args) {
		new File(HomeController.uploadDir).mkdir();
		SpringApplication.run(PracticeApplication.class, args);
	}

}
