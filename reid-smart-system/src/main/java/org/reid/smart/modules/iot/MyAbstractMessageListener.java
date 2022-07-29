package org.reid.smart.modules.iot;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import top.reid.smart.iot.mqttv3.listener.AbstractMessageListener;

/**
 * <p>
 * 自定义 MQTT 客户端消息监听器
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/7/29
 * @Version V1.0
 **/
@Slf4j
public class MyAbstractMessageListener extends AbstractMessageListener  {
    @Override
    protected void msgArrived(String topic, MqttMessage mqttMessage) {
        log.info("---------------------------------自定义 MQTT 客户端接收到消息---------------------------------");
        log.info("消息主题 topic：{}", topic);
        log.info("消息内容：{}", new String(mqttMessage.getPayload()));
        log.info("消息质量 qos：{}", mqttMessage.getQos());
        log.info("-------------------------------------------------------------------------------------");
    }
}
