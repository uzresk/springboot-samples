package jp.gr.java_conf.uzresk.sbex.web.service;

import jp.gr.java_conf.uzresk.sbex.entity.Account;
import jp.gr.java_conf.uzresk.sbex.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginAccountDetailService implements UserDetailsService {

	@Autowired
	AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String accountId)
			throws UsernameNotFoundException {
		Account account = accountRepository.findOne(accountId);
		if (account == null) {
			throw new UsernameNotFoundException("Account is not found");
		}

		return new LoginAccountDetails(account);
	}
}
