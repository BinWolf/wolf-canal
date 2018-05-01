package com.wolf.canal.client;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created on 18/4/30 17:47.
 *
 * @author wolf
 */
@Component
public class WolfCanalClientStart {
    protected final static Logger logger = LoggerFactory.getLogger(WolfCanalClientStart.class);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${wolf.canal.topic}")
    private String destination;

    @PostConstruct
    public void start() {

        // 基于zookeeper动态获取canal server的地址，建立链接，其中一台server发生crash，可以支持failover
        CanalConnector connector = CanalConnectors.newClusterConnector("127.0.0.1:2181", destination, "", "");

        final WolfCanalClient clientTest = new WolfCanalClient(kafkaTemplate, destination);
        clientTest.setConnector(connector);
        clientTest.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                logger.info("## stop the canal client");
                clientTest.stop();
            } catch (Throwable e) {
                logger.warn("##something goes wrong when stopping canal:", e);
            } finally {
                logger.info("## canal client is down.");
            }
        }));
    }
}