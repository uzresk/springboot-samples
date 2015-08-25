package jp.gr.java_conf.uzresk.sbex.web.service;

import jp.gr.java_conf.uzresk.sbex.entity.Account;
import lombok.Getter;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class LoginAccountDetails extends User {

	private static final long serialVersionUID = 1L;

	@Getter
	private final Account account;

	public LoginAccountDetails(Account account) {
		super(account.getAccountId(), account.getPassword(), AuthorityUtils
				.createAuthorityList("ROLE_USER"));
		this.account = account;
	}
}
