package com.sbapplication.service;

import com.sbapplication.entity.User;
import com.sbapplication.repo.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {
            "Tikka",
            "Pelu",
            "Angoori"
    })
    public void testFind(String name) {
        //User user = userRepository.findByUsername(name);
        //assertTrue(!user.getJournalEntries().isEmpty());
        assertNotNull(userRepository.findByUsername(name));
        //assertEquals(4, 2+2);
    }


    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(User user) {
        assertNotNull(userService.saveNewUser(user));
    }


    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "2,4,6"
    })
    public void test(int a, int b, int expected ) {
         assertEquals(expected , a+b);
    }
}
