package com.datayes.websample.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.datayes.websample.SpringContextTests;
import com.datayes.websample.task.MyTask;

public class SampleServiceTest extends SpringContextTests {
	@Autowired
	SampleService sampleService;
	@Autowired
	MyTask myTask;
	
//	@Test
	public void testAspect() {
		sampleService.aspectTest("allen");
	}
	
	@Test
	public void testTask(){
		
	}
}
