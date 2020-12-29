package com.github.jms.factory.bean;

import com.github.jms.factory.AbstractConnectionFactory;
import com.rabbitmq.client.Address;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * RabbitMQ连接工厂
 *
 * 连接池
 * 发送实力
 * 监听实例
 *
 * 支持@AutoConfiguration
 *
 */
public class AmqpConnectFacotryBean implements AbstractConnectionFactory<Connection> {


    private static final String VIRTUAL_HOST_KEY = "shinestar.amqp.queue.virtualHost";
    private static final String USERNAME_KEY = "shinestar.amqp.queue.userName";
    private static final String PASSWORD_KEY = "shinestar.amqp.queue.password";
    private static final String ADDRESSES_KEY = "shinestar.amqp.queue.addresses";

    public void connect(Properties properties) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 基本参数配置
        connectionFactory.setVirtualHost(properties.getProperty(VIRTUAL_HOST_KEY));
        connectionFactory.setUsername(properties.getProperty(USERNAME_KEY));
        connectionFactory.setPassword(properties.getProperty(PASSWORD_KEY));

        String[] addresses = StringUtils.split(properties.getProperty(ADDRESSES_KEY, ","));
        List<Address> addressList = new ArrayList<>();
        for (String address:addresses) {
            String[] hostAndPort = StringUtils.split(address, ":");
            addressList.add(new Address(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
        }

        Connection connection = null;
        try {
            connection = connectionFactory.newConnection(addressList.toArray(new Address[addressList.size()]));
            connection.createChannel();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }



}
