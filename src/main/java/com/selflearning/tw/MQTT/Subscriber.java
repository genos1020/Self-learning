package com.selflearning.tw.MQTT;

public class Subscriber {

    public static void main(String[] args) {
        String serverURI="tcp://localhost:1883";
        String clientID="demo_mqtt000";
        MqttSubscriber mqttSubscriber = new MqttSubscriber(serverURI, clientID);
        mqttSubscriber.subscribe("ETC/TradeMsg");
    }
}
