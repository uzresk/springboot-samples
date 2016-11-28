package jp.gr.java_conf.uzresk.springboot.demo.web.service;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import jp.gr.java_conf.uzresk.springboot.demo.dao.MemberDao;
import jp.gr.java_conf.uzresk.springboot.demo.entity.Member;

@Component
public class LoginUserDetailsService implements UserDetailsService {

	Logger logger = LoggerFactory.getLogger(LoginUserDetailsService.class);

	@Autowired
	MemberDao memberDao;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

		Optional<Member> member = memberDao.selectByUserId(userId);

		if (member.isPresent()) {
			Member m = member.get();
			return new LoginUserDetails(m, getAuthorities(m));
		} else {
			throw new UsernameNotFoundException(userId + " is not found.");
		}
	}

	public Collection<GrantedAuthority> getAuthorities(Member member) {

		String authority = member.getAuthority();

		if ("1".equals(authority)) {
			return AuthorityUtils.createAuthorityList("ROLE_GENERAL", "ROLE_ADMIN");
		} else {
			return AuthorityUtils.createAuthorityList("ROLE_GENERAL");
		}
	}

}
