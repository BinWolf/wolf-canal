package com.wolf.canal;

import com.wolf.canal.kafka.KafkaProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WolfCanalApplicationTests {

    @Autowired
    private KafkaProvider kafkaProvider;

    @Test
    public void testSender() {

        String topic = "wolf-canal";
        for (int i = 0; i < 10000000; i++) {
            kafkaProvider.send(topic, "Say hi to wolf times :" + i);
            System.out.println("--------------  " + i);
        }

    }

    @Test
    public void contextLoads() {
    }


}
