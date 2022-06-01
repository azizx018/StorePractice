package net.yorksolutions.users;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @InjectMocks
    UserService service;

    @Mock
    UserAccountRepository repository;

    @Test
    void it() {


    }
}

