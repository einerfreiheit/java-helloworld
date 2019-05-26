/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.handler.messaging;

import io.netty.buffer.ByteBufInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.api.core.RoutingType;
import org.apache.activemq.artemis.api.core.client.ClientConsumer;
import org.apache.activemq.artemis.api.core.client.ClientSession;
import org.apache.activemq.artemis.core.protocol.mqtt.MQTTSession;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author stepa
 */
@Component
public class Listener {

    private Logger log = Logger.getLogger(Listener.class.getCanonicalName());

    @Autowired
    private MQTT mqtt;

    @PostConstruct
    private void init() throws ActiveMQException, Exception {
        CallbackConnection callbackConnection = mqtt.callbackConnection();
        callbackConnection.connect(new Callback<Void>() {
            @Override
            public void onSuccess(Void value) {
                log.info("Listener connected");
            }

            @Override
            public void onFailure(Throwable value) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        callbackConnection.listener(new org.fusesource.mqtt.client.Listener() {
            @Override
            public void onConnected() {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onDisconnected() {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onPublish(UTF8Buffer topic, Buffer body, Runnable ack) {
                log.log(Level.INFO, "{0} : {1}", new Object[]{topic.toString(), body.toString()});
                ack.run();
            }

            @Override
            public void onFailure(Throwable value) {
                log.log(Level.SEVERE, value.getMessage());
            }
        });
        callbackConnection.subscribe(new Topic[]{new Topic("#", QoS.AT_LEAST_ONCE)}, new Callback<byte[]>() {
            @Override
            public void onSuccess(byte[] value) {
                log.info("Listener started");
            }

            @Override
            public void onFailure(Throwable value) {
                log.log(Level.SEVERE, value.getMessage());
            }
        });
    }
}
