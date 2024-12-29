package com.sbapplication.service.impl;

import com.sbapplication.model.SentimentData;
import com.sbapplication.service.EmailService;
import com.sbapplication.service.SentimentConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerServiceImpl implements SentimentConsumerService {

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "weekly_sentiments",groupId = "weekly-sentiment-group")
    @Override
    public void consume(SentimentData sentimentData) {
      sendEmail(sentimentData);
      System.out.println("Message Consumed");
    }

    private void sendEmail(SentimentData sentimentData) {
        emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
    }
}
