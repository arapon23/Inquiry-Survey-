package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // デフォルトの設定を代用（以前はXMLで設定が必要）
public class MyFirstApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyFirstApplication.class, args);
	}

}
