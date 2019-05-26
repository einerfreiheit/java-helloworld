/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.handler.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mycompany.mavenproject1.model.Device;
import com.mycompany.mavenproject1.model.repository.Devices;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.api.core.RoutingType;
import org.apache.activemq.artemis.api.core.client.ClientMessage;
import org.apache.activemq.artemis.api.core.client.ClientProducer;
import org.apache.activemq.artemis.api.core.client.ClientSession;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.Listener;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author stepa
 */
@RestController
@RequestMapping(path = "/dev")
public class DeviceHandler {

    private Logger log = Logger.getLogger(DeviceHandler.class.getCanonicalName());

    @Autowired
    Devices devices;

    @Autowired
    private MQTT mqtt;

    public CallbackConnection connection;

    private Jackson2JsonEncoder jsonEncoder = new Jackson2JsonEncoder();

    @Value("${myapp.jms.topic.devadd:device_added}")
    private String devAddTopic;

    @PostConstruct
    private void init() throws Exception {
        connection = mqtt.callbackConnection();
        connection.connect(new Callback<Void>() {
            @Override
            public void onSuccess(Void value) {
                log.log(Level.INFO, "Notification topic {0} started", devAddTopic);
            }

            @Override
            public void onFailure(Throwable value) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public List<Device> list() {
        return devices.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/add/")
    public Device add(@RequestParam String name) throws JsonProcessingException {
        Device d = new Device();
        d.setName(name);

        connection.publish(devAddTopic, jsonEncoder.getObjectMapper().writeValueAsBytes(d), QoS.AT_LEAST_ONCE, false, new Callback<Void>() {
            @Override
            public void onSuccess(Void value) {
                log.info("Msg sent");
            }

            @Override
            public void onFailure(Throwable value) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        return devices.save(d);
    }

}
