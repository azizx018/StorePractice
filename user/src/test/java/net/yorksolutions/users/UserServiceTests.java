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



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @InjectMocks
    @Spy
    UserService service;

    @Mock
    UserAccountRepository repository;

    private UserAccount user1 = new UserAccount("user1", "password1", false);
    private UserAccount user2 = new UserAccount("user2", "password2", false);
    private UserAccount admin1 = new UserAccount("admin1", "password3", true);

    @Test
    void itShouldNotThrowWhenAnOwnerRequestsUsers() {
        final Long id = 0L;
        when(repository.isOwner(id)).thenReturn(true);
        assertDoesNotThrow(() ->service.viewAllUsers(id));

    }
    @Test
    void itShouldThrowWhenANonOwnerRequestsUsers() {
        final Long id = 0L;
        when(repository.isOwner(id)).thenReturn(false);
        final ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->service.viewAllUsers(id));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
    }

    @Test
    void itShouldThrowUnAuthWhenNonOwnerRequestsDelete() {
        when(repository.isOwner(user1.id)).thenReturn(false);

        final ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                ()->service.deleteUser(user1.id, user2.id));

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
    }
    @Test
    void itShouldDeleteUserWhenOwnerRequestsDelete() {
        final Long id = 0L;
        final Long id2 = 1L;
        doNothing().when(service).ownerCheck(id);
        service.deleteUser(id,id2);
        verify(repository).deleteById(id2);

    }
}

