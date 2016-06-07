package jp.gr.java_conf.uzresk.sbex.web.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.gr.java_conf.uzresk.sbex.entity.Account;
import jp.gr.java_conf.uzresk.sbex.web.service.LoginAccountDetails;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;

@Controller
public class TwitterLoginController {

	@Autowired
	private HttpSession session;

	@Autowired
	private HttpServletRequest request;

	@RequestMapping("/oauth/twitter/auth")
	String loginTwitter() {

		Twitter twitter = new TwitterFactory().getInstance();
		
		RequestToken requestToken = null;
		try {
			requestToken = twitter.getOAuthRequestToken("http://127.0.0.1:8080/app/oauth/twitter/access");

			session.setAttribute("requestToken", requestToken);
		} catch (TwitterException e) {
			throw new RuntimeException(e);
		}
		return "redirect:" + requestToken.getAuthenticationURL();
	}

	@RequestMapping("/oauth/twitter/access")
	String loginTwitterAccess() {
		
		Configuration conf = ConfigurationContext.getInstance();
		RequestToken requestToken = (RequestToken) session.getAttribute("requestToken");

		// token secretが無い場合はエラーとする。
		if (requestToken == null || StringUtils.isBlank(requestToken.getTokenSecret())) {
			throw new RuntimeException("token secret is null.");
		}

		AccessToken accessToken = new AccessToken(requestToken.getToken(), requestToken.getTokenSecret());
		OAuthAuthorization oath = new OAuthAuthorization(conf);

		oath.setOAuthAccessToken(accessToken);
		String verifier = request.getParameter("oauth_verifier");
		try {
			accessToken = oath.getOAuthAccessToken(verifier);
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		Account account = new Account(accessToken.getScreenName(), new Long(accessToken.getUserId()).toString());
		LoginAccountDetails loginAccountDetails = new LoginAccountDetails(account);

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginAccountDetails, null,
				AuthorityUtils.createAuthorityList("ROLE_USER"));
		SecurityContextHolder.getContext().setAuthentication(token);

		session.setAttribute("accessToken", accessToken);

		return "redirect:/top";
	}
}
