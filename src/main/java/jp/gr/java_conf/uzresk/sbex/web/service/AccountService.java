package jp.gr.java_conf.uzresk.sbex.web.service;

import javax.transaction.Transactional;

import jp.gr.java_conf.uzresk.sbex.entity.Account;
import jp.gr.java_conf.uzresk.sbex.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional(rollbackOn = Exception.class)
public class AccountService {

	@Autowired
	AccountRepository accountRepository;

	public void create(Account account) {
		accountRepository.save(account);
	}

	public Account findAccount(String accountId) {
		return accountRepository.findOne(accountId);
	}
}
