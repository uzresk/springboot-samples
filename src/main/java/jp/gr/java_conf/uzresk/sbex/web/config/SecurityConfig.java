package jp.gr.java_conf.uzresk.sbex.web.config;

import jp.gr.java_conf.uzresk.sbex.web.mfa.MFAAuthenticationConfigurer;
import jp.gr.java_conf.uzresk.sbex.web.mfa.MFAWebAuthenticationDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig {

	@Configuration
	public static class DefaultWebSecurityConfigurerAdapter extends
			WebSecurityConfigurerAdapter {

		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		@Autowired
		public void configureGlobal(UserDetailsService userDetailsService,
				AuthenticationManagerBuilder auth) throws Exception {

			// MFAコードで認証するためのプロバイダをAuthenticationManagerBuilderに追加
			MFAAuthenticationConfigurer configurer = new MFAAuthenticationConfigurer(
					userDetailsService).passwordEncoder(passwordEncoder());
			auth.apply(configurer);
		}


		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/css/**", "/js/**","/fonts/**", "/img/**");
		}

		protected void configure(HttpSecurity http) throws Exception {

			// アカウント登録ページはanonymous. loginPageもanonymousになる。
			http.authorizeRequests().antMatchers("/account/**").anonymous();

			http.authorizeRequests().antMatchers("/loginForm").permitAll()
					.anyRequest().authenticated();

			http.formLogin()
					.authenticationDetailsSource(
							MFAWebAuthenticationDetails::new) // MFAコードを取得するための設定
					.loginProcessingUrl("/login").loginPage("/loginForm")
					.failureUrl("/loginForm?error")
					.defaultSuccessUrl("/top", true)
					.usernameParameter("accountid")
					.passwordParameter("password").and();

			http.logout()
					.logoutRequestMatcher(
							new AntPathRequestMatcher("/logout**"))
					.logoutSuccessUrl("/loginForm");

			// セッションIDを変更し、セッションを新しく作り直す
			http.sessionManagement().sessionFixation().newSession();
		}
	}
}
