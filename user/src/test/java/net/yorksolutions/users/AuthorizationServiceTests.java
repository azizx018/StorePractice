package net.yorksolutions.users;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Mockito.lenient;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceTests {

    @InjectMocks
    AuthorizationService service;

    @Mock
    UserAccountRepository repository;

    @Mock
    HashMap<UUID, Long> tokenMap;

    @Test
    void itShouldReturnUnauthorizedWhenUsernameIsWrong() {
        final String username = "bad username";
        final String password = "good password";
        when(repository.findByUsernameAndPassword(username, password))
                .thenReturn(Optional.empty());
        lenient().when(repository.findByUsernameAndPassword(not(eq(username)), eq(password)))
                .thenReturn(Optional.of(new UserAccount()));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> service.login(username, password));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());

    }

    @Test
    void itShouldReturnUnauthorizedWhenPasswordIsWrong() {
        final String username = "bad username";
        final String password = "good password";
        when(repository.findByUsernameAndPassword(username, password))
                .thenReturn(Optional.empty());
        lenient().when(repository.findByUsernameAndPassword(eq(username), not(eq(password))))
                .thenReturn(Optional.of(new UserAccount()));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> service.login(username, password));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());

    }
    @Test
    void itShouldMapTheUUIDToIdWhenLoginSuccess() {
        final String username = "bad username";
        final String password = "good password";
        final Long id = 0L;
        UserAccount expected = new UserAccount(username, password, false);
        expected.setId(id);
        when(repository.findByUsernameAndPassword(eq(username), not(eq(password))))
                .thenReturn(Optional.of(expected));
        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);
        when(tokenMap.put(captor.capture(), eq(id))).thenReturn(id);
        UUID token = service.login(username, password);
        assertEquals(token, captor.getValue());

    }

    //Logout Tests
    @Test
    void itShouldRemoveUUIDTokenFromTokenMapWhenLogoutAndTokenIsInTokenMap() {
        final UUID token = UUID.randomUUID();
        when(tokenMap.containsKey(token)).thenReturn(false);
        when(tokenMap.remove(token)).thenReturn(0L);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->service.logout(token));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());

    }

    //register tests
    //it should check if the user is unique
    //it should return conflict is the user is not unique

    @Test
    void itShouldSaveNewCustomerAccountWhenRegisterIsCalledAndUsernameIsUnique() {
        final String username = "some username";
        final String password = "some password";

        final UserAccount expected = new UserAccount(username, password, false);
        ArgumentCaptor<UserAccount> captor = ArgumentCaptor.forClass(UserAccount.class);
        when(repository.save(captor.capture())).thenReturn(new UserAccount());

        when(repository.findByUsername(username)).thenReturn(Optional.empty());
        service.registerCustomer(username, password);
        assertEquals(expected, captor.getValue());
    }

    @Test
    void itShouldThrowConflictIfUsernameExists() {
        final String  username = "bad username";
        final String password = "some password";
        when(repository.findByUsername(username)).thenReturn(Optional.of(new UserAccount()));
        final ResponseStatusException exception = assertThrows(ResponseStatusException.class, ()-> service.registerCustomer(username, password));
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @Test
    void itShouldSaveANewUserAccountWhenUserIsUnique() {

    }
    @Test
    void itShouldThrowUnauthorizedWhenTokenIsBad() {

    }
    @Test
    void itShouldThrowOkWhenTokenIsGood() {}

}
