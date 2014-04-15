package com.datayes.websample.dao;

import com.datayes.websample.domain.Account;

public interface AccountDAO extends BaseDAO<Account> {
	public Account findById(Long id);
}
