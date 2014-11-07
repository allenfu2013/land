package com.datayes.websample.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT")
public class Account {
	@Id
	@GeneratedValue
	@Column(name = "ACCOUNT_ID", unique = true)
	private Long accountId;

	@Column(name = "ACCOUNT_NAME")
	private String accountName;

	@Column(name = "CREATE_TIME", nullable = false)
	private Date createTime = new Date();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
	private Set<AccountRole> accountRoles;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Set<AccountRole> getAccountRoles() {
		return accountRoles;
	}

	public void setAccountRoles(Set<AccountRole> accountRoles) {
		this.accountRoles = accountRoles;
	}
	
}
