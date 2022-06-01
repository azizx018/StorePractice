package net.yorksolutions.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorizationControllerTests {
    @LocalServerPort
    int port;

    @Autowired
    AuthorizationController controller;

    @Mock
    AuthorizationService service;

    @BeforeEach
    void setup() {controller.setService(service);}

    @Test
    void itShouldRespondUnauthWhenTokenIsWrong() {
        TestRestTemplate rest = new TestRestTemplate();
        final UUID token = UUID.randomUUID();
        String url = "http://localhost:" + port + "/isAuthorized?token=" + token;
        doThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED)).when(service).isAuthorized(token);
        final ResponseEntity<Void> response = rest.getForEntity(url, Void.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

    }
}
