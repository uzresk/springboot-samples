package jp.gr.java_conf.uzresk.springboot.demo.web.service;

import jp.gr.java_conf.uzresk.springboot.demo.entity.Member;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginUserDetails extends User {

    private final Member member;

    public LoginUserDetails(Member member, Collection<GrantedAuthority> grantedAuthorities) {

        super(member.getUserId(), member.getPassword(), grantedAuthorities);
        this.member = member;
    }

}
