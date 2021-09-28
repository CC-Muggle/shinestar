package com.github.shinestar.rabbitmq;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.http.client.Client;
import com.rabbitmq.http.client.domain.ExchangeInfo;
import com.rabbitmq.http.client.domain.OverviewResponse;
import com.rabbitmq.http.client.domain.QueueInfo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class ApplicationStarter {

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setUsername("root");
        connectionFactory.setPassword("root");
        connectionFactory.setVirtualHost("/");
        // 单机版就是这个样子
//        connectionFactory.setHost("127.0.0.1");
//        connectionFactory.setPort(5672);
        try {
            Connection  connection = connectionFactory.newConnection(Arrays.asList(new Address("127.0.0.1", 5672)));
            Channel channel = connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        // 访问rabbitmq管理台的api接口
        Client client = new Client("http://127.0.0.1:15672/", "root", "root");
        List<ExchangeInfo> exchangeInfos = client.getExchanges();

        List<QueueInfo> queueInfos = client.getQueues();
        queueInfos.get(0).getMessagesUnacknowledged();


    }
}
