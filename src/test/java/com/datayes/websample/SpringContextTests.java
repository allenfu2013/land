package com.datayes.websample;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
//@Transactional
public abstract class SpringContextTests {

	@Before
	public void before() {
		System.out.println("Start time: " + new Date().toLocaleString());
	}
	
	@After
	public void after() {
		System.out.println("End time: " + new Date().toLocaleString());
	}
}
