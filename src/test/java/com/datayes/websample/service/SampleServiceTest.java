package com.datayes.websample.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.datayes.websample.SpringContextTests;

public class SampleServiceTest extends SpringContextTests {
	@Autowired
	SampleService sampleService;
	
	@Test
	public void testAspect() {
		sampleService.aspectTest("allen");
	}
}
