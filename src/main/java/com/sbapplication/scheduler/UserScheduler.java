package com.sbapplication.scheduler;

import com.sbapplication.cache.AppCache;
import com.sbapplication.entity.JournalEntry;
import com.sbapplication.entity.User;
import com.sbapplication.enums.Sentiment;
import com.sbapplication.model.SentimentData;
import com.sbapplication.service.EmailService;
import com.sbapplication.service.impl.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    private AppCache appCache;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Scheduled(cron="0 0/10 * 1/1 * ?")
    public void fetchUsersAndSendMail() {
        List<User> users =userRepositoryImpl.getUserForSA();
        for(User user :users)
        {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDate.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentCount = new HashMap<>();
            for(Sentiment sentiment : sentiments) {
                if(sentiment != null) {
                    sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment,0) +1);
                }
            }
            Sentiment mostFreqSentiment =null;
            int maxCount=0;
            for(Map.Entry<Sentiment,Integer> entry: sentimentCount.entrySet()) {
                if(entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFreqSentiment = entry.getKey();
                }
            }
            if(mostFreqSentiment != null) {
                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days " + mostFreqSentiment).build();
                try{
                    kafkaTemplate.send("weekly_sentiments", sentimentData.getEmail(), sentimentData);
                    System.out.print("Message Produced");
                }catch (Exception e){
                    emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
                    System.out.print("Exception Occured");
                }
            }
        }

    }

    @Scheduled(cron = "0/10 0 0 ? * *")
    public void clearAppChache() {
       appCache.init();
    }
}
