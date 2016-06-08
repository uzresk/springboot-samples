package jp.gr.java_conf.uzresk.sbex.web.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

	ServletRegistrationBean h2servletRegistration() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
		registrationBean.addUrlMappings("/h2-console");
		return registrationBean;
	}
}
