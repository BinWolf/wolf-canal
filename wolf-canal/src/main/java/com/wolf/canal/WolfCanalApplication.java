package com.wolf.canal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

/**
 * @author wolf
 */
@SpringBootApplication
public class WolfCanalApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(WolfCanalApplication.class, args);
		new CountDownLatch(1).await();
	}
}
