package jp.gr.java_conf.uzresk.sbex.web.common;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpSession;

import jp.gr.java_conf.uzresk.sbex.log.Log;

public class WebUtils {

	private static Log logger = new Log(WebUtils.class);

	private static final String[] EXCLUDE_KEY = {
			"SPRING_SECURITY_CONTEXT",
			"org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN",
			"region" };

	private static final List<String> EXCLUDE_KEY_LIST = Arrays
			.asList(EXCLUDE_KEY);

	public static void clearSession(HttpSession session) {

		Enumeration<String> enu = session.getAttributeNames();
		while (enu.hasMoreElements()) {
			String sessionKey = enu.nextElement();
			if (!EXCLUDE_KEY_LIST.contains(sessionKey)) {
				logger.info("remove session key[" + sessionKey + "]");
				session.removeAttribute(sessionKey);
			}

		}
	}
}
