package com.selflearning.tw.MQTT;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@Slf4j
public class MqttSubscriber {
    
    private MqttClient mqttClient;

    public MqttSubscriber(String SERVER_URI, String CLIENT_ID){
    	try {
            MemoryPersistence persistence = new MemoryPersistence();
            mqttClient = new MqttClient(SERVER_URI, CLIENT_ID,persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            mqttClient.connect(connOpts);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void subscribe(String topic) {
        if (mqttClient == null){
            return;
        }
        try {
            mqttClient.subscribe(topic);
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    System.out.println("connect loss");
                }

                @Override
                public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                    log.info(topic);
                    log.info(mqttMessage.toString());

                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    System.out.println("delivery isComplete:" + iMqttDeliveryToken.isComplete());
                }
            });
        } catch (MqttException e) {
            System.out.println(e.getMessage());
        }
    }
}
