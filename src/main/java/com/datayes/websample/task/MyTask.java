package com.datayes.websample.task;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyTask {

	@Scheduled(fixedDelay = 2 * 1000)
	public void task() {
		System.out.println("##### " + Thread.currentThread().getName() + new Date().toLocaleString());
	}
}
