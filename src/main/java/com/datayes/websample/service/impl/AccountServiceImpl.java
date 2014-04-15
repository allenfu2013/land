package com.datayes.websample.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.datayes.websample.dao.AccountDAO;
import com.datayes.websample.domain.Account;
import com.datayes.websample.service.AccountService;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
	@Resource
	private AccountDAO accountDAO;

	@Override
	public void createAccount(Account account) {
		accountDAO.saveOrUpdate(account);
	}

}
