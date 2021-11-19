package org.reid.smart.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * <p>
 * 单体启动
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2021/11/19
 * @Version V1.0
 **/
@Slf4j
@SpringBootApplication
public class ReidSmartSystemApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(ReidSmartSystemApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = Objects.requireNonNull(env.getProperty("server.servlet.context-path")).trim();
        log.info("\n----------------------------------------------------------\n\t" +
                "Application Reid-Smart-Pro is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttps://" + ip + ":" + port + path + "/\n\t" +
                "Swagger文档: \thttps://" + ip + ":" + port + path + "/doc.html\n" +
                "----------------------------------------------------------");
    }
}
