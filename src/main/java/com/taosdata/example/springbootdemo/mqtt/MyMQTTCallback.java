package com.taosdata.example.springbootdemo.mqtt;

import cn.hutool.core.util.CharsetUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taosdata.example.springbootdemo.constant.CommonConstant;
import com.taosdata.example.springbootdemo.domain.GasInfo;
import com.taosdata.example.springbootdemo.service.GasInfoService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author WXY
 * @date 2022/6/29 20:43
 */
@Component
public class MyMQTTCallback implements MqttCallbackExtended {

    //手动注入
    private MqttConfiguration mqttConfiguration = SpringUtils.getBean(MqttConfiguration.class);

    private static final Logger log = LoggerFactory.getLogger(MyMQTTCallback.class);

    private MyMQTTClient myMQTTClient;

    public MyMQTTCallback(MyMQTTClient myMQTTClient) {
        this.myMQTTClient = myMQTTClient;
    }


//    @Autowired
//    private GasInfoService gasInfoService;
    private GasInfoService gasInfoService = SpringUtils.getBean(GasInfoService.class);

    /**
     * 丢失连接，可在这里做重连
     * 只会调用一次
     *
     * @param throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {
        log.error("mqtt connectionLost 连接断开，5S之后尝试重连: {}", throwable.getMessage());
        long reconnectTimes = 1;
        while (true) {
            try {
                if (MyMQTTClient.getClient().isConnected()) {
                    //判断已经重新连接成功  需要重新订阅主题 可以在这个if里面订阅主题  或者 connectComplete（方法里面）  看你们自己选择
                    log.warn("mqtt reconnect success end  重新连接  重新订阅成功");
                    return;
                }
                reconnectTimes+=1;
                log.warn("mqtt reconnect times = {} try again...  mqtt重新连接时间 {}", reconnectTimes, reconnectTimes);
                MyMQTTClient.getClient().reconnect();
            } catch (MqttException e) {
                log.error("mqtt断连异常", e);
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e1) {
            }
        }
    }

    /**
     * @param topic
     * @param mqttMessage
     * @throws Exception
     * subscribe后得到的消息会执行到这里面
     */
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        String payload = new String(mqttMessage.getPayload());
        log.info("接收消息主题 : {}，接收消息内容 : {}", topic, payload);

       // log.info("接收消息主题 : {}，接收消息内容 : {}", topic, new String(mqttMessage.getPayload()));
       // myMQTTClient.publish("test1111","testTopic");
        // 创建ObjectMapper对象
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        // 将JSON字符串映射到类对象
        try {
            GasInfo gasInfo = objectMapper.readValue(payload, GasInfo.class);
            //System.out.println(gasInfo);
            gasInfoService.gasInfoResolve(gasInfo);
        }catch (JsonProcessingException exception){
            System.out.println(exception);
        }catch (Exception exception){
            System.out.println(exception);
        }

//
//        //发布消息主题
//        if (topic.equals("embed/resp")){
//            Map maps = (Map) JSON.parse(new String(mqttMessage.getPayload(), CharsetUtil.UTF_8));
//            //你自己的业务接口
//            //insertCmdResults(maps);
//        }
//        //接收报警主题
//        if (topic.equals("embed/warn")){
//            Map maps = (Map) JSON.parse(new String(mqttMessage.getPayload(), CharsetUtil.UTF_8));
//            //你自己的业务接口
//           // insertPushAlarm(maps);
//        }
    }


    /**
     *连接成功后的回调 可以在这个方法执行 订阅主题  生成Bean的 MqttConfiguration方法中订阅主题 出现bug
     *重新连接后  主题也需要再次订阅  将重新订阅主题放在连接成功后的回调 比较合理
     * @param reconnect
     * @param serverURI
     */
    @Override
    public  void  connectComplete(boolean reconnect,String serverURI){
        log.info("MQTT 连接成功，连接方式：{}",reconnect?"重连":"直连");
        //订阅主题
        myMQTTClient.subscribe(mqttConfiguration.topic1, 1);
        myMQTTClient.subscribe(mqttConfiguration.topic2, 1);
        myMQTTClient.subscribe(mqttConfiguration.topic3, 1);
        myMQTTClient.subscribe(mqttConfiguration.topic4, 1);
    }

    /**
     * 消息到达后
     * subscribe后，执行的回调函数
     *
     * @param s
     * @param mqttMessage
     * @throws Exception
     */
    /**
     * publish后，配送完成后回调的方法
     *
     * @param iMqttDeliveryToken
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info("==========deliveryComplete={}==========", iMqttDeliveryToken.isComplete());
    }
}

