package com.datayes.websample.dao.impl;

import org.springframework.stereotype.Repository;

import com.datayes.websample.dao.AccountRoleDAO;
import com.datayes.websample.domain.AccountRole;

@Repository("accountRoleDAO")
public class AccountRoleDAOImpl extends BaseDAOImpl<AccountRole> implements AccountRoleDAO {

}
