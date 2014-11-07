package com.datayes.websample.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.datayes.websample.dao.AccountDAO;
import com.datayes.websample.domain.Account;

@Repository("accountDAO")
public class AccountDAOImpl extends BaseDAOImpl<Account> implements AccountDAO {

	@Override
	public Account findById(Long id) {
		String hql = "from Account where accountId = ?";
		List list = getHibernateTemplate().find(hql, id);
		if (!list.isEmpty()) {
			return (Account) list.get(0);
		}
		return null;
	}

}
