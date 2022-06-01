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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

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
    //this is not needed- duplicated with the below test
    @Test
    void itShouldRespondAcceptedWhenTokenIsCorrect() {
        TestRestTemplate rest = new TestRestTemplate();
        final UUID token = UUID.randomUUID();
        String url = "http://localhost:" + port + "/isAuthorized?token=" + token;
        doThrow(new ResponseStatusException(HttpStatus.ACCEPTED)).when(service).isAuthorized(token);
        final ResponseEntity<Void> response = rest.getForEntity(url, Void.class);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

    }
    @Test
    void itShouldResponseWithATokenWhenLoginIsValid() {
        TestRestTemplate rest = new TestRestTemplate();
        final String username = "some username";
        final String password = "some password";
        String url = "http://localhost:" + port + "/login?username=" + username + "&password=" + password;
        final UUID token = UUID.randomUUID();
        when(service.login(username,password)).thenReturn(token);
        final ResponseEntity<UUID> response = rest.getForEntity(url, UUID.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
    @Test
    void itShouldReturnConflictWhenUsernameIsTakenForOwnerAccount() {
        final TestRestTemplate rest = new TestRestTemplate();
        final String username = "some username";
        final String password = "some password";
        String url = "http://localhost:" + port + "/registerOwner?username=" + username + "&password=" + password;
        doThrow(new ResponseStatusException(HttpStatus.CONFLICT)).when(service).registerOwner(username, password);
        final ResponseEntity<Void> response = rest.getForEntity(url, Void.class);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

    }
    @Test
    void itShouldReturnConflictWhenUsernameIsTakenForCustomerAccount() {
        final TestRestTemplate rest = new TestRestTemplate();
        final String username = "some username";
        final String password = "some password";
        String url = "http://localhost:" + port + "/registerCustomer?username=" + username + "&password=" + password;
        doThrow(new ResponseStatusException(HttpStatus.CONFLICT)).when(service).registerCustomer(username, password);
        final ResponseEntity<Void> response = rest.getForEntity(url, Void.class);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

    }
    @Test
    void itShouldReturnAcceptedWhenActiveTokenIsPassedAndLogout() {
        TestRestTemplate rest = new TestRestTemplate();
        final UUID token = UUID.randomUUID();
        String url = "http://localhost:" + port + "/logout?token=" + token;
        doThrow(new ResponseStatusException(HttpStatus.ACCEPTED)).when(service).logout(token);
        final ResponseEntity<Void> response = rest.getForEntity(url, Void.class);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

    }

}
