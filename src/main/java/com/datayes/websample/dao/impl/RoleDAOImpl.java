package com.datayes.websample.dao.impl;

import org.springframework.stereotype.Repository;

import com.datayes.websample.dao.RoleDAO;
import com.datayes.websample.domain.Role;

@Repository("roleDAO")
public class RoleDAOImpl extends BaseDAOImpl<Role> implements RoleDAO {

}
