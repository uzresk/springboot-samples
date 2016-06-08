package jp.gr.java_conf.uzresk.sbex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "jp.gr.java_conf.uzresk.sbex.repository")
@EntityScan(basePackages = "jp.gr.java_conf.uzresk.sbex.entity")
@ComponentScan(basePackages = "jp.gr.java_conf.uzresk.sbex.web")
public class App {

//	private static Log logger = new Log(App.class);

	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);

//		ApplicationContext ctx = SpringApplication.run(App.class, args);
//		String[] beanNames = ctx.getBeanDefinitionNames();
//		Arrays.sort(beanNames);
//		for (String beanName : beanNames) {
//			logger.info(beanName);
//		}
	}
}
