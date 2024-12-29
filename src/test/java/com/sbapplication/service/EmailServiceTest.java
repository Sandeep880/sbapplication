package com.sbapplication.service;

import com.sbapplication.scheduler.UserScheduler;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private UserScheduler userScheduler;
    @Autowired
    private EmailService emailService;

    @Disabled
    @Test
    public void testSendMail() {
        emailService.sendEmail("asandeep2099@gmail.com",
                "Testing Java mail sender",
                "Hi, aap kaise hain ?");
    }

    @Test
    public void testfetchUsersAndSendMail() {
        userScheduler.fetchUsersAndSendMail();
    }


}
