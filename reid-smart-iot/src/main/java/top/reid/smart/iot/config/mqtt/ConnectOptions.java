package top.reid.smart.iot.config.mqtt;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * <p>
 * MQTT 链接配置
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/7/7
 * @Version V1.0
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "reid.mqtt.options")
public class ConnectOptions {
    /**
     * 客户端可以连接的备用 MQTT 服务器，用英文逗号间隔。
     * <p>
     * 当启动连接尝试时，客户端将从列表中的第一个 serverURI 开始，并通过列表工作，直到与服务器建立连接，
     * 如果成功链接一台服务器，后面的服务器将不会尝试去链接。如果无法与任何服务器建立连接，则连接尝试将失败。
     * </p>
     * <p>
     * 指定客户端可以连接的服务器列表有多种用途：
     * </p>
     * <ol>
     * <li>高可用性和可靠的消息传递</li>
     * <p>
     * 一些 MQTT 服务器支持高可用性功能，其中两个或更多“相等”的 MQTT 服务器共享状态。
     * MQTT 客户端可以连接到任何“同等”的服务器，并确保无论客户端连接到哪个服务器，消息都能可靠传递并保持持久订阅。
     * </p>
     * <p>
     * 如果需要持久订阅和/或可靠的消息传递，则 cleansession 标志必须设置为 false。
     * </p>
     * <li>狩猎清单</li>
     * <p>
     * 可以指定一组不“相等”的服务器（如在高可用性选项中）。由于服务器之间没有共享状态，因此可靠的消息传递和持久订阅无效。
     * 如果使用搜寻列表模式，则 cleansession 标志必须设置为 true
     * </p>
     * </ol>
     */
    private String serverUrls;
    /**
     * 链接用户名
     **/
    private String username;
    /**
     * 链接密码
     **/
    private String password;
    /**
     * 连接超时时间(单位秒)
     **/
    private int connectionTimeout = 30;
    /***
     * 是否清除 session 会话信息，当客户端 disconnect 时 cleanSession 为 true 会清除之前的所有订阅信息。<br>
     * 注意：cleanSession = true 客户端掉线后，服务器端会清除 session，客户端再次链接服务器后链接前的订阅主题将会全部失效。
     */
    private Boolean cleanSession = false;
    /**
     * 终止 executor 服务时等待的时间(以秒为单位)
     */
    private int executorServiceTimeout = 1;
    /**
     * 每隔多长时间（秒）发送一个心跳包（默认60秒）
     **/
    private int keepaliveInterval = 60;
    /**
     * 消息最大堵塞数 默认10
     */
    private int maxInflight = 10;
    /**
     * 重连接间隔毫秒数，默认为128000ms
     */
    private int maxReconnectDelay = 128000;
    /**
     * 链接安全 SSL 设置
     */
    private SSL ssl;
    /**
     * 设置连接的“最后遗嘱和遗嘱”（LWT）。<br>
     * 如果此客户端意外失去与服务器的连接，服务器将使用提供的详细信息向自己发布消息。
     */
    private Will will;


    @Data
    @Configuration
    @ConfigurationProperties(prefix = "reid.mqtt.options.ssl")
    protected class SSL {
        /**
         * 是否启用
         */
        private Boolean enable = false;
        /***
         * 客户端证书地址
         */
        private String clientKeyStore = "";

        /***
         * 客户端秘钥
         */
        private String clientKeyStorePassword = "";

        protected SocketFactory getSocketFactory() {
            return enable ? getSSLSocketFactory() : SocketFactory.getDefault();
        }

        private SSLSocketFactory getSSLSocketFactory(){
            SSLContext sslContext = null;
            try {
                InputStream keyStoreStream = Files.newInputStream(Paths.get(new URL(ssl.clientKeyStore).getPath()));
                sslContext = SSLContext.getInstance("TLS");
                TrustManagerFactory trustManagerFactory = TrustManagerFactory
                        .getInstance(TrustManagerFactory.getDefaultAlgorithm());
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(keyStoreStream, ssl.clientKeyStorePassword.toCharArray());
                trustManagerFactory.init(keyStore);
                sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            } catch (NoSuchAlgorithmException | KeyStoreException | IOException | CertificateException |
                     KeyManagementException e) {
                e.printStackTrace();
            }
            return sslContext != null ? sslContext.getSocketFactory() : null;
        }
    }

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "reid.mqtt.options.will")
    protected class Will {
        /***
         * 设置“遗嘱”消息的话题主题，若客户端与服务器之间的连接意外中断，服务器将发布客户端的“遗嘱”消息。
         */
        private String topic = "";

        /***
         * 设置“遗嘱”消息的内容，默认是长度为零的消息。
         */
        private String message = "";

        /***
         * 设置“遗嘱”消息的 QoS，默认为2
         */
        private int qos = 2;
        /***
         * 若想要在发布“遗嘱”消息时拥有 retain 选项，则为true。
         */
        private Boolean retained = false;
    }

    /**
     * 获取链接选项，包含控制客户端如何连接到服务器的选项集
     * @param host 主链接地址
     * @return 链接选项
     */
    public MqttConnectOptions getConnectOptions(String host){
        MqttConnectOptions options = new MqttConnectOptions();
        if (StrUtil.isNotEmpty(serverUrls)) {
            String urls = host + "," + serverUrls;
            options.setServerURIs(urls.split(","));
        }
        if (StrUtil.isNotEmpty(this.username)) {
            options.setUserName(this.username);
        }
        if (StrUtil.isNotEmpty(this.password)) {
            options.setPassword(this.password.toCharArray());
        }
        options.setCleanSession(this.cleanSession);
        options.setConnectionTimeout(this.connectionTimeout);
        options.setExecutorServiceTimeout(this.executorServiceTimeout);
        options.setKeepAliveInterval(this.keepaliveInterval);
        options.setMaxInflight(this.maxInflight);
        options.setMaxReconnectDelay(this.maxReconnectDelay);
        if (this.ssl != null) {
            options.setSocketFactory(this.ssl.getSocketFactory());
        }
        if (this.will != null) {
            options.setWill(this.will.topic, this.will.message.getBytes(), this.will.qos, this.will.retained);
        }
        return options;
    }

}
