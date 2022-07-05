package com.selflearning.tw.MQTT;

import com.selflearning.tw.vo.Album;

public class Publisher {

    public static void main(String[] args) {
        String serverURI="tcp://localhost:1883";
        String clientID="demo_mqtt000";
        MqttProducer mqttProducer = new MqttProducer(serverURI, clientID);

        Album a1 = new Album(1, "ooh-ahh");
        mqttProducer.send("ETC/TradeMsg", 1, false, String.valueOf(a1));
    }
}
