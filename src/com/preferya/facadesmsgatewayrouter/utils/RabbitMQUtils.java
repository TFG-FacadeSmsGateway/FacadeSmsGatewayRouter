/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.utils;

import com.preferya.facadesmsgatewayrouter.models.*;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;

/**
 *
 * @author Sergio
 */
public class RabbitMQUtils {
    
    //private static final String TASK_QUEUE_NAME = "internalQueue";
    
    private String task_queue_name;
    
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private QueueingConsumer consumer;
    
    /*public RabbitMQUtils() throws IOException{
        this.factory = new ConnectionFactory();
        this.factory.setHost("localhost");
        this.connection = this.factory.newConnection();
        this.channel = this.connection.createChannel();
        
        //this.channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        this.channel.queueDeclare(task_queue_name, true, false, false, null);
        
    }*/
    
    public RabbitMQUtils(String countryCode) throws IOException{
        this.task_queue_name = countryCode;
        
        this.factory = new ConnectionFactory();
        this.factory.setHost("localhost");
        this.connection = this.factory.newConnection();
        this.channel = this.connection.createChannel();
        
        //this.channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        this.channel.queueDeclare(task_queue_name, true, false, false, null);
        
        this.channel.basicQos(1);
    
        this.consumer = new QueueingConsumer(channel);
        this.channel.basicConsume(task_queue_name, false, consumer);
        
    }
    
    //This method closes the channel and the connection.
    public void closeConnection() throws IOException {
        this.channel.close();
        this.connection.close();
    }
    
    public String reciveMessage() throws InterruptedException, IOException {
        String _ret;
        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        _ret = new String(delivery.getBody());
        this.channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        return _ret;
    }
    
    /*public void sendMessage(MessageEntity message) throws IOException {
        String _sndMess = message.toString();
        channel.basicPublish( "", task_queue_name, MessageProperties.PERSISTENT_TEXT_PLAIN, _sndMess.getBytes());
        //channel.basicPublish( "", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, _sndMess.getBytes());
    }*/
    
}
