package com.example.batch.batchdemo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class BatchDemoApplication {

	static void main(String[] args) {
		println "Running"
		SpringApplication.run(BatchDemoApplication.class, args)
	}

}
