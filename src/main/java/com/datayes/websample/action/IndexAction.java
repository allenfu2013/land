package com.datayes.websample.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@ParentPackage("websample")
@Namespace("/")
@Results({ 
		@Result(name = "success", location = "/index.jsp"),
})
public class IndexAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private String username;
	private Integer age;

	@Action(value="/page1", results={@Result(name="result1",location="/WEB-INF/jsp/page1.jsp")})
	public String page1() {
		return "result1";
	}

	@Action(value="/page2", results={@Result(name="result2",location="/WEB-INF/jsp/page2.jsp")})
	public String page2() {
		return "result2";
	}

	@Action(value = "/insertUser", interceptorRefs={@InterceptorRef("defaultStack"), @InterceptorRef(value = "validation")})
	@Validations(
		requiredStrings ={
				@RequiredStringValidator(fieldName="username", message="姓名不能为空！"),
				@RequiredStringValidator(fieldName="age", message="年龄不能为空！"),
		}
	)
	public String insertUser() {
		System.out.println("username: " + username + ", age: " + age);
		return SUCCESS;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
