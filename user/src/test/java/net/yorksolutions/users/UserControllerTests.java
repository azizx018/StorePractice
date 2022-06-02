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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class UserControllerTests {
    @LocalServerPort
    int port;

    @Autowired
    UserController controller;

    @Mock
    UserService service;

    @BeforeEach
    void setup() {
        controller.setUserService(service);
    }

    @Test
    void itShouldRespondUnauthorizedWhenUserIsNotOwnerAndViewAllUsersIsCalled() {
        TestRestTemplate rest = new TestRestTemplate();
        final UUID requestingUserToken = UUID.randomUUID();
        String url = "http://localhost:" + port + "/viewAllUsers?requestingUserToken=" + requestingUserToken;
        //doNothing().when(service).viewAllUsers(testId);
//        doThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED))
//                .when(service).viewAllUsers();
        final ResponseEntity<Void> response = rest.getForEntity(url,Void.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
    @Test
    void itShouldRespondOkWhenOwnerCallsViewAllUsers() {
        TestRestTemplate rest = new TestRestTemplate();
        final UUID requestingUserToken = UUID.randomUUID();
        String url = "http://localhost:" + port + "/viewAllUsers?requestingUserToken=" + requestingUserToken;
        doThrow(new ResponseStatusException(HttpStatus.OK))
                .when(service).viewAllUsers();
        final ResponseEntity<Void> response = rest.getForEntity(url,Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
    @Test
    void itShouldRespondUnauthorizedWhenUserIsNotOwnerAndDeleteIsCalled() {
        TestRestTemplate rest = new TestRestTemplate();
        final UUID requestingUserToken = UUID.randomUUID();
        final Long idToBeDeleted = 2L;
        String url = "http://localhost:" + port +
                "/deleteUser?requestingUserToken=" + requestingUserToken +
                "&idToBeDeleted=" + idToBeDeleted;
        //doNothing().when(service).deleteUser(requestingUserToken,idToBeDeleted);
        //when(service.ownerCheck(requestingUserToken));

//        doThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED))
//          .when(service).ownerCheck(requestingUserToken);

//        doThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED))
//                .when(service).deleteUser(idToBeDeleted);
        final ResponseEntity<Void> response = rest.getForEntity(url,Void.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

    }
    @Test
    void ifShouldRespondOkWhenOwnerDeletesUser() {
        TestRestTemplate rest = new TestRestTemplate();
        final UUID requestingUserToken = UUID.randomUUID();
        final Long idToBeDeleted = 2L;
        String url = "http://localhost:" + port +
                "/deleteUser?requestingUserToken=" + requestingUserToken +
                "&idToBeDeleted=" + idToBeDeleted;
        //doNothing().when(service).deleteUser(requestingUserToken,idToBeDeleted);
        //when(service.ownerCheck(requestingUserToken));

//        doThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED))
//          .when(service).ownerCheck(requestingUserToken);

        doThrow(new ResponseStatusException(HttpStatus.OK))
                .when(service).deleteUser(idToBeDeleted);
        final ResponseEntity<Void> response = rest.getForEntity(url,Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

}
