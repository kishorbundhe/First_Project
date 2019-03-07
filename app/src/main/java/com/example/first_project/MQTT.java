package com.example.first_project;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MQTT {
    static int flagconnected = 0;
    static int flagunsubscribe = 0;
    static int flagsubscribe = 0;
    static int flagdisconnected = 0;
    static String clientId;
    static IMqttToken token;
    static MqttAndroidClient client;
    static String topic;
    static int qos = 1;
    IMqttMessageListener iMqttMessageListener;

    static String urlformqqtconnection = "tcp://broker.hivemq.com:1883";

    MQTT(Context context, String url){

        clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(context, url, clientId);
        client.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });




    }





    public int ClientConnect( ) {



        try {
            token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    flagconnected = 1;


                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    flagconnected = 0;

                }
            });
        } catch (MqttException e) {
            flagconnected = 2;
        }
        return flagconnected;
    }


    public int Subscribe(String topic) {

        this.topic = topic;

        try {
            IMqttToken subToken = client.subscribe(topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override



                public void onSuccess(IMqttToken asyncActionToken) {
                    flagsubscribe = 1;

                }


                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // The subscription could not be performed, maybe the user was not
                    // authorized to subscribe on the specified topic e.g. using wildcards
                    flagsubscribe = 0;
                }
            });
        } catch (MqttException e) {
            flagsubscribe = 2;
        }
        return flagsubscribe;
    }

    public int Unsubscribe(String topic) {
        this.topic = topic;
        try {
            IMqttToken unsubToken = client.unsubscribe(topic);
            unsubToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // The subscription could successfully be removed from the client
                    flagunsubscribe = 1;
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // some error occurred, this is very unlikely as even if the client
                    // did not had a subscription to the topic the unsubscribe action
                    // will be successfully
                    flagunsubscribe = 0;
                }
            });
        } catch (MqttException e) {


            flagunsubscribe = 2;
        }
        return flagunsubscribe;
    }
    public int Disconnected(String topic) {
        this.topic = topic;
        try {
            IMqttToken disconToken = client.disconnect();
            disconToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // we are now successfully disconnected
                    flagdisconnected=1;
                }


                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // something went wrong, but probably we are disconnected anyway
                    flagdisconnected=0;
                }
            });
        } catch (MqttException e) {
            flagdisconnected=2;
        }

    return flagdisconnected;
    }

}
