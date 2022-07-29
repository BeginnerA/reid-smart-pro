package org.reid.smart.modules.iot;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import top.reid.smart.iot.mqttv3.listener.AbstractPublishListener;

/**
 * <p>
 * 自定义 MQTT 异步发布消息监听器
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/7/29
 * @Version V1.0
 **/
@Slf4j
public class MyAbstractPublishListener extends AbstractPublishListener {
    @Override
    protected void success(IMqttToken mqttToken) {
        log.info("自定义 MQTT 异步发布消息监听器：MQTT 异步发布消息成功");
    }

    @Override
    protected void fail(IMqttToken mqttToken, Throwable throwable) {
        log.info("自定义 MQTT 异步发布消息监听器：MQTT 异步发布消息失败");
    }
}
