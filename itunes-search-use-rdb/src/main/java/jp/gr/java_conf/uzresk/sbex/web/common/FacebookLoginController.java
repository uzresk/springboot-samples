package jp.gr.java_conf.uzresk.sbex.web.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.gr.java_conf.uzresk.sbex.entity.Account;
import jp.gr.java_conf.uzresk.sbex.web.service.LoginAccountDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.User;

@Controller
public class FacebookLoginController {

	@Autowired
	private HttpSession session;

	@Autowired
	private HttpServletRequest request;

	@RequestMapping("/oauth/facebook/auth")
	String loginFacebook() {

		Facebook facebook = new FacebookFactory().getInstance();

		session.setAttribute("facebook", facebook);

		return "redirect:"
				+ facebook
						.getOAuthAuthorizationURL("http://localhost:8080/app/oauth/facebook/access");
	}

	@RequestMapping("/oauth/facebook/access")
	String loginFacebookAccess() {

		Facebook facebook = (Facebook) request.getSession().getAttribute(
				"facebook");

		if (facebook == null) {
			throw new RuntimeException("request token is null.");
		}

		String code = request.getParameter("code");
		User user = null;

		try {
			// Facebook Exceptionが発生しないこと
			facebook.getOAuthAccessToken(code);
			// 自分の情報を取得する。
			user = facebook.getMe();
		} catch (FacebookException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		Account account = new Account(user.getName().replaceAll(" ", ""), user.getId());
		LoginAccountDetails loginAccountDetails = new LoginAccountDetails(
				account);

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				loginAccountDetails, null,
				AuthorityUtils.createAuthorityList("ROLE_USER"));
		SecurityContextHolder.getContext().setAuthentication(token);

		return "redirect:/top";
	}
}
