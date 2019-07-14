/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.config;

import java.net.URISyntaxException;
import org.apache.activemq.artemis.api.core.client.ActiveMQClient;
import org.apache.activemq.artemis.api.core.client.ClientSession;
import org.apache.activemq.artemis.api.core.client.ClientSessionFactory;
import org.apache.activemq.artemis.api.core.client.ServerLocator;
import org.apache.activemq.artemis.core.config.Configuration;
import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.impl.ActiveMQServerImpl;
import org.fusesource.mqtt.client.MQTT;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;

/**
 *
 * @author stepa
 */
@org.springframework.context.annotation.Configuration
@ComponentScan(basePackageClasses = com.mycompany.mavenproject1.handler.messaging.Package.class)
public class Messaging {
    
    @Bean
    public Configuration getConfiguration() throws Exception {
        ConfigurationImpl configuration = new ConfigurationImpl();
        configuration.addAcceptorConfiguration("in-vm", "vm://0");
        configuration.addAcceptorConfiguration("tcp", "tcp://127.0.0.1:61616?protocols=MQTT");
        configuration.setSecurityEnabled(false);
        return configuration;
    }
    @Bean("amq-server")
    public ActiveMQServer getActiveMQ() throws Exception {
        ActiveMQServerImpl server = new ActiveMQServerImpl(getConfiguration());
        server.start();
        return server;
    }

    @Bean
    @DependsOn("amq-server")
    public ClientSession getCoreClientProducer() throws Exception {
        ServerLocator serverLocator = ActiveMQClient.createServerLocator("vm://0?protocols=MQTT");

        ClientSessionFactory sessionFactory = serverLocator.createSessionFactory();
        ClientSession session = sessionFactory.createSession();
        session.start();

        return session;
    }

    @Bean
    @DependsOn("amq-server")
    public MQTT getMQTTClient() throws URISyntaxException, Exception {
        MQTT mqtt = new MQTT();
        mqtt.setHost("tcp://127.0.0.1:61616");
        return mqtt;
    }
}
