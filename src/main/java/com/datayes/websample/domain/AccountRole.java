package com.datayes.websample.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT_ROLE")
public class AccountRole {
	@Id
	@GeneratedValue
	@Column(name = "ACCOUNT_ROLE_ID", nullable = false, unique = true)
	private Long accountRoleId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ACCOUNT_ID", nullable = false)
	private Account account;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE_ID", nullable = false)
	private Role role;

	@Column(name = "CREATE_TIME")
	private Date createTime = new Date();

	public Long getAccountRoleId() {
		return accountRoleId;
	}

	public void setAccountRoleId(Long accountRoleId) {
		this.accountRoleId = accountRoleId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
