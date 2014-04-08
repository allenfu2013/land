package com.datayes.websample.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT")
public class Account {
	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false, unique = true)
	private Long id;

	@Column(name = "ACCOUNT_NAME", nullable = false)
	private String accountName;

	@Column(name = "ACCOUNT_SIGN")
	private String accountSign;

	@Column(name = "ACCOUNT_EMAIL", nullable = false)
	private String accountEmail;

	@Column(name = "ACCOUNT_PWD", nullable = false)
	private String accountPwd;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountSign() {
		return accountSign;
	}

	public void setAccountSign(String accountSign) {
		this.accountSign = accountSign;
	}

	public String getAccountEmail() {
		return accountEmail;
	}

	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}

	public String getAccountPwd() {
		return accountPwd;
	}

	public void setAccountPwd(String accountPwd) {
		this.accountPwd = accountPwd;
	}

}
