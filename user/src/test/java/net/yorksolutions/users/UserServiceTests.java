package net.yorksolutions.users;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @InjectMocks
    @Spy
    UserService service;

    @Mock
    UserAccountRepository repository;

    private final UUID requestingUserToken = UUID.randomUUID();

    @Test
    void itShouldNotThrowWhenAnOwnerRequestsUsers() {
        when(repository.isOwner(requestingUserToken)).thenReturn(true);
        assertDoesNotThrow(() ->service.viewAllUsers(requestingUserToken));

    }
    @Test
    void itShouldThrowWhenANonOwnerRequestsUsers() {
        when(repository.isOwner(requestingUserToken)).thenReturn(false);
        final ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () ->service.viewAllUsers(requestingUserToken));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
    }

    @Test
    void itShouldThrowUnAuthWhenNonOwnerRequestsDelete() {

        when(repository.isOwner(requestingUserToken)).thenReturn(false);

        final ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                ()->service.deleteUser(requestingUserToken, 5L));

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
    }
    @Test
    void itShouldDeleteUserWhenOwnerRequestsDelete() {
        final Long idToBeDeleted = 1L;
        doNothing().when(service).ownerCheck(requestingUserToken);
        service.deleteUser(requestingUserToken,idToBeDeleted);
        verify(repository).deleteById(idToBeDeleted);
    }
}

