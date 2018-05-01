package com.wolf.canal.kafka;

import com.wolf.canal.bo.CanalBO;
import com.wolf.canal.util.CanalParseUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created on 18/5/1 09:02.
 *
 * @author wolf
 */
@Component
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "${wolf.canal.topic}")
    public void consumerWolfCanal(ConsumerRecord<String, String> consumerRecord) {
        CanalBO canalBO = CanalParseUtil.parseCanalBO(consumerRecord.value().toString());
        LOGGER.info(canalBO.toString());
    }
}
