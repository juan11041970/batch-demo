package com.example.batch.batchdemo

import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@EnableAutoConfiguration
class BatchDemoApplicationTests {

	@Test
	void contextLoads() {
		println "Groovy works!!!!"
	}
}
