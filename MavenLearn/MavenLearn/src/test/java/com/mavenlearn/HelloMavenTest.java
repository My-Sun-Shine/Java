package com.mavenlearn;

import org.junit.*;

public class HelloMavenTest{
	

	@Test
	public void testM1(){
		
		HelloMaven hm = new HelloMaven();
		String actual = hm.m1();//实际值
		String expected = "Hello";//期望值
		Assert.assertEquals(expected,actual);//断言
	}
}