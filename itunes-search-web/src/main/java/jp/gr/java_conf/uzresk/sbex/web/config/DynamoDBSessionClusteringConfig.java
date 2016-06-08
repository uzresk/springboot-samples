package jp.gr.java_conf.uzresk.sbex.web.config;

import org.apache.catalina.Context;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

import com.amazonaws.services.dynamodb.sessionmanager.DynamoDBSessionManager;
import com.amazonaws.tomcatsessionmanager.amazonaws.services.s3.model.Region;

//@Configuration
public class DynamoDBSessionClusteringConfig {

	@Bean
	EmbeddedServletContainerFactory configure() {
		final TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
		factory.addContextCustomizers(new TomcatContextCustomizer() {
			@Override
			public void customize(Context context) {
				context.setBackgroundProcessorDelay(1);
				context.setManager(dynamoDBSessionManager());
			}
		});
		return factory;
	}
	
	private DynamoDBSessionManager dynamoDBSessionManager() {
		 final DynamoDBSessionManager manager = new DynamoDBSessionManager();
		 manager.setAwsAccessKey("TODO");
		 manager.setAwsSecretKey("TODO");
		 manager.setReadCapacityUnits(10);
		 manager.setWriteCapacityUnits(5);
		 manager.setRegionId(Region.AP_Tokyo.getFirstRegionId());
		 manager.setCreateIfNotExist(true);
		 manager.setProxyHost("TODO");
		 manager.setProxyPort(8080);
		 // session alive time(sec)
		 manager.setSessionMaxAliveTime(30);
		 // セッション永続化のタイミング
		 // maxIdleBackup + processExpiresFrequency * engine.backgroundProcessorDelay
		 manager.setMaxIdleBackup(1);
		 manager.setProcessExpiresFrequency(1);
		 // Restart時に永続化するか否か
		 manager.setSaveOnRestart(true);

		 return manager;
	}
}
