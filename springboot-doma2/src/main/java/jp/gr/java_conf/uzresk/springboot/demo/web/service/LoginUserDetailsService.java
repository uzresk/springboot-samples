package jp.gr.java_conf.uzresk.springboot.demo.web.service;

import jp.gr.java_conf.uzresk.springboot.demo.dao.MemberDao;
import jp.gr.java_conf.uzresk.springboot.demo.entity.Member;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {

    private final MemberDao memberDao;

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
