package com.datayes.websample.action;

import java.util.Date;
import java.util.Set;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;

import com.datayes.websample.dao.AccountDAO;
import com.datayes.websample.dao.AccountRoleDAO;
import com.datayes.websample.dao.RoleDAO;
import com.datayes.websample.domain.Account;
import com.datayes.websample.domain.AccountRole;
import com.datayes.websample.domain.Role;
import com.datayes.websample.service.AccountService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
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
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private AccountRoleDAO accountRoleDAO;
	
	@Action(value="/page1", results={@Result(name="result1",location="/WEB-INF/jsp/page1.jsp")})
	@SkipValidation
	public String page1() {
		Account account = new Account();
		account.setAccountId(1L);
//		account.setAccountName("hope");
		account.setCreateTime(new Date());
		accountDAO.saveOrUpdate(account);
		/*Account account = new Account();
		account.setAccountName("hope");
		accountDAO.saveOrUpdate(account);
		
		Role role = new Role();
		role.setRoleName("ADMIN");
		roleDAO.saveOrUpdate(role);
		
		AccountRole accountRole = new AccountRole();
		accountRole.setAccount(account);
		accountRole.setRole(role);
		accountRoleDAO.saveOrUpdate(accountRole);*/
		
//		Account account = accountDAO.findById(1L);
//		Set<AccountRole> accountRoles = account.getAccountRoles();
//		for (AccountRole accountRole : accountRoles) {
//			String roleName = accountRole.getRole().getRoleName();
//			System.out.println(roleName);
//		}
		return "result1";
	}

	@Action(value="/page2", results={@Result(name="result2",location="/WEB-INF/jsp/page2.jsp")})
	@InputConfig(resultName="success")
	public String page2() {
		return "result2";
	}

	@Action(value = "/insertUser")
	@InputConfig(resultName="success")
	@Validations(
		requiredFields={
			@RequiredFieldValidator(fieldName="age",message="年龄不能为空!")
		},
		requiredStrings={ 
			@RequiredStringValidator(fieldName="username",message="用户名不能为空!"), 
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
