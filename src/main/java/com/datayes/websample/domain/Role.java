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
@Table(name = "ROLE")
public class Role {
	@Id
	@GeneratedValue
	@Column(name = "ROLE_ID", nullable = false, unique = true)
	private Long roleId;

	@Column(name = "ROLE_NAME", nullable = false, unique = true)
	private String roleName;

	@Column(name = "CREATE_TIME")
	private Date createTime = new Date();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
	private Set<AccountRole> accountRoles;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
	private Set<RolePermission> rolePermissions;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public Set<RolePermission> getRolePermissions() {
		return rolePermissions;
	}

	public void setRolePermissions(Set<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}

}
