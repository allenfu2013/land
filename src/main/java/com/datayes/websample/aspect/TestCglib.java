package com.datayes.websample.aspect;

public class TestCglib {
	
	public String test(String name){
		System.out.println("test name-->" + name);
		return name;
	}

	public static void main(String[] args) {
		CglibAspect aspect = new CglibAspect();
		TestCglib cglib = (TestCglib) aspect.getProxy(new TestCglib());
		cglib.test("allen");
	}

}
