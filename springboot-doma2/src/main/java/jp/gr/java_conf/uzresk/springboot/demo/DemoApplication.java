package jp.gr.java_conf.uzresk.springboot.demo;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "jp.gr.java_conf.uzresk.springboot.demo, jp.gr.java_conf.uzresk.springboot.framework")
public class DemoApplication {

    private static Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {

        // Dump Env
        // Properties props = System.getProperties();
        // props.keySet().stream().forEach(s -> System.out.println(s + ":" +
        // props.getProperty(s.toString())));

        // Start H2 Server mode
        startH2Server();

        SpringApplication.run(DemoApplication.class, args);
    }

    private static void startH2Server() {

        try {
            Server h2 = Server.createTcpServer().start();
            if (h2.isRunning(true)) {
                logger.info("H2 server was started. port[" + h2.getPort() + "]");
            }

        } catch (SQLException e) {
            throw new RuntimeException("can't start h2 server.");
        }

    }
}
