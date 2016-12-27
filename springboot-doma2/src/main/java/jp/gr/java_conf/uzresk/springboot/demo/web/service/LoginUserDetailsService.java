package jp.gr.java_conf.uzresk.springboot.demo.web.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jp.gr.java_conf.uzresk.springboot.demo.dao.MemberDao;
import jp.gr.java_conf.uzresk.springboot.demo.entity.Member;
import org.springframework.stereotype.Service;

@Service
public class LoginUserDetailsService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(LoginUserDetailsService.class);

    @Autowired
    MemberDao memberDao;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        Optional<Member> member = memberDao.selectByUserId(userId);

        member.orElseThrow(() -> new UsernameNotFoundException(userId + " is not found."));

        return new LoginUserDetails(member.get(), getAuthorities(member));
    }

    public Collection<GrantedAuthority> getAuthorities(Optional<Member> member) {

        String authority = member.get().getAuthority();

        if ("1".equals(authority)) {
            return AuthorityUtils.createAuthorityList("ROLE_GENERAL", "ROLE_ADMIN");
        } else {
            return AuthorityUtils.createAuthorityList("ROLE_GENERAL");
        }
    }

}
