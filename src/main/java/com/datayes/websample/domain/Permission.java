package com.datayes.websample.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PERMISSION")
public class Permission {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PERMISSION_ID", nullable = false, unique = true)
	private Long permissionId;

	@Column(name = "PERMISSION_NAME", nullable = false, unique = true)
	private String permissionName;

	@Column(name = "CREATE_TIME")
	private Date createTime = new Date();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "permission")
	private Set<RolePermission> rolePermissions;

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
