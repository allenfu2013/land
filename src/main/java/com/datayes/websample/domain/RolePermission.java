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
@Table(name = "ROLE_PERMISSION")
public class RolePermission {
	@Id
	@GeneratedValue
	@Column(name = "ROLE_PERMISSION_ID")
	private Long rolePermissionId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE_ID")
	private Role role;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PERMISSION_ID")
	private Permission permission;

	@Column(name = "CREATE_TIME")
	private Date createTime = new Date();

	public Long getRolePermissionId() {
		return rolePermissionId;
	}

	public void setRolePermissionId(Long rolePermissionId) {
		this.rolePermissionId = rolePermissionId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
