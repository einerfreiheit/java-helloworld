/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.handler.web;

import com.mycompany.mavenproject1.model.Device;
import com.mycompany.mavenproject1.model.repository.Devices;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.api.core.RoutingType;
import org.apache.activemq.artemis.api.core.SimpleString;
import org.apache.activemq.artemis.api.core.client.ActiveMQClient;
import org.apache.activemq.artemis.api.core.client.ClientConsumer;
import org.apache.activemq.artemis.api.core.client.ClientMessage;
import org.apache.activemq.artemis.api.core.client.ClientProducer;
import org.apache.activemq.artemis.api.core.client.ClientSession;
import org.apache.activemq.artemis.api.core.client.ClientSessionFactory;
import org.apache.activemq.artemis.api.core.client.ServerLocator;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
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

    @Autowired
    Devices devices;
    
    @Autowired
    private ClientSession session;
    
    private ClientProducer producer;
    
    private Jackson2JsonEncoder jsonEncoder = new Jackson2JsonEncoder();
    
    @Value("${myapp.jms.topic.devadd:device_added}")
    private String devAddTopic;
    
    @PostConstruct
    private void init() throws ActiveMQException {
        session.createQueue(devAddTopic, RoutingType.MULTICAST, devAddTopic, false);
        producer = session.createProducer(devAddTopic);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public List<Device> list() {
        return devices.findAll();
    }
    
    
    @RequestMapping(method = RequestMethod.GET, path = "/add/")
    public Device add(@RequestParam String name) throws Exception {
        Device d = new Device();
        d.setName(name);
        
        ClientMessage message = session.createMessage(false);
        message.getBodyBuffer().writeString(jsonEncoder.getObjectMapper().writeValueAsString(d));
        producer.send(message);
        return devices.save(d);
    }

}
