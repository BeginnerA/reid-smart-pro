package org.reid.smart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import top.reid.smart.base.spring.annotation.MapperScan;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * <p>
 * 单体启动
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2021/11/19
 * @Version V1.0
 **/
@MapperScan("${reid.mybatis.mapperScan}")
@Slf4j
@SpringBootApplication
public class ReidSmartApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(ReidSmartApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = Objects.requireNonNull(env.getProperty("server.servlet.context-path")).trim();
        log.info("\n----------------------------------------------------------\n\t" +
                "Application Reid-Smart-Pro is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/doc.html\n\t" +
                "External: \thttps://" + ip + ":" + port + path + "/doc.html\n\t" +
                "Swagger文档: \thttps://" + ip + ":" + port + path + "/doc.html\n" +
                "----------------------------------------------------------");
    }
}
