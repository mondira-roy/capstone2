package com.company.capstone2.levelupconsumer;

import com.company.capstone2.levelupconsumer.model.Levelup;
import com.company.capstone2.levelupconsumer.util.feign.LevelupClient;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
@Component
public class Listener {

    @Autowired
    private final LevelupClient client;

    public Listener(LevelupClient client) {
        this.client = client;
    }

    @RabbitListener(queues = LevelupConsumerApplication.QUEUE_NAME)
    public void receiveMessage(Levelup msg) {
        System.out.println(msg.toString());
        Levelup levelup = new Levelup();
        levelup.setLevelupId(msg.getLevelupId());
        levelup.setCustomerId(msg.getCustomerId());
        levelup.setPoint(msg.getPoint());
        levelup.setMemberDate(msg.getMemberDate());
        client.addLevelup(levelup);
    }
}
