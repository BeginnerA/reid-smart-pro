package org.reid.smart.modules.iot;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.reid.smart.common.api.vo.Result;
import top.reid.smart.iot.mqttv3.pojo.Publish;
import top.reid.smart.iot.mqttv3.pojo.Subscribe;
import top.reid.smart.iot.service.mqtt.PublisherService;
import top.reid.smart.iot.service.mqtt.SubscribeService;
import top.reid.smart.iot.service.mqtt.UnsubscribeService;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/7/27
 * @Version V1.0
 **/
@Api(tags = "MQTT 测试")
@RestController
@RequestMapping("/iot")
public class IotController {

    @Resource
    SubscribeService subscribeService;
    @Resource
    PublisherService publisherService;
    @Resource
    UnsubscribeService unsubscribeService;

    @PostMapping("/subscribeToInform")
    @ApiOperation(value = "订阅消息-同步")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "topic", value = "订阅主题", dataType = "String", required = true),
    })
    public Result<?> subscribeToInform(String topic) {
        Subscribe subscribe = new Subscribe();
        subscribe.setTopic(topic);
        subscribe.setMyMessageListener(new MyAbstractMessageListener());
        return Result.ok(subscribeService.subscribe(subscribe));
    }

    @PostMapping("/publishInform")
    @ApiOperation(value = "推送消息-同步")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "message", value = "推送的消息报文", dataType = "String", required = true),
            @ApiImplicitParam(name = "topic", value = "订阅主题", dataType = "String", required = true),
    })
    public Result<?> publishInform(String message, String  topic) {
        Publish publish = new Publish();
        publish.setTopic(topic);
        publish.setMessage(message);
        publish.setCallbackAsync(new MyAbstractPublishListener());
        return Result.ok(publisherService.publish(publish));
    }

    @PostMapping("/unsubscribeInform")
    @ApiOperation(value = "取消订阅-同步")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "topic", value = "订阅主题", dataType = "String", required = true),
    })
    public Result<?> unsubscribeInform(String  topic) {
        return Result.ok(unsubscribeService.unsubscribe(topic));
    }
}
