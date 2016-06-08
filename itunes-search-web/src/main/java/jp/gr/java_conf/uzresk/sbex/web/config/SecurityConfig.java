package jp.gr.java_conf.uzresk.sbex.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jp.gr.java_conf.uzresk.sbex.web.mfa.MFAAuthenticationConfigurer;
import jp.gr.java_conf.uzresk.sbex.web.mfa.MFAWebAuthenticationDetails;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Configuration
	public static class DefaultWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		@Autowired
		public void configureGlobal(UserDetailsService userDetailsService, AuthenticationManagerBuilder auth)
				throws Exception {

			MFAAuthenticationConfigurer configurer = new MFAAuthenticationConfigurer(userDetailsService)
					.passwordEncoder(passwordEncoder());
			auth.apply(configurer);
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/css/**", "/js/**", "/fonts/**", "/img/**");
		}

		protected void configure(HttpSecurity http) throws Exception {

			// @formatter:off
			http
				.csrf()
					.disable()
				.authorizeRequests()
					.antMatchers("/account/**").permitAll()
					.antMatchers("/oauth/twitter/**").permitAll()
					.antMatchers("/oauth/facebook/**").permitAll()
					.antMatchers("/loginForm").permitAll()
					.antMatchers("/h2-console/**").permitAll()
		    		.and()
			    .formLogin()
			    	.authenticationDetailsSource(MFAWebAuthenticationDetails::new)
			    	.loginProcessingUrl("/login")
					.loginPage("/loginForm")
					.failureUrl("/loginForm?error")
					.defaultSuccessUrl("/top", true)
					.usernameParameter("accountid")
					.passwordParameter("password").and()
				.sessionManagement()
					.sessionFixation()
					.newSession()
					.and()
				.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))
					.logoutSuccessUrl("/loginForm")
					.invalidateHttpSession(true);
			// @formatter:on
		}
	}
}
