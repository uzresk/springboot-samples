package jp.gr.java_conf.uzresk.sbex.web.mfa;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.UserDetailsAwareConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MFAAuthenticationConfigurer extends
		UserDetailsAwareConfigurer<AuthenticationManagerBuilder, UserDetailsService> {

	private MFAAuthenticationProvider provider = new MFAAuthenticationProvider();

	private final UserDetailsService userDetailsService;

	public MFAAuthenticationConfigurer(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
		this.provider.setUserDetailsService(userDetailsService);
	}

	public MFAAuthenticationConfigurer passwordEncoder(PasswordEncoder passwordEncoder) {
		this.provider.setPasswordEncoder(passwordEncoder);
		return this;
	}

	@Override
	public void configure(AuthenticationManagerBuilder builder) throws Exception {
		this.provider = postProcess(this.provider);
		builder.authenticationProvider(this.provider);
	}

	@Override
	public UserDetailsService getUserDetailsService() {
		return this.userDetailsService;
	}

}
