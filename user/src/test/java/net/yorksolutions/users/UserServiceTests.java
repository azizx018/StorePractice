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

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @InjectMocks
    @Spy
    UserService service;

    @Mock
    UserAccountRepository repository;

    UserAccount ownerAccount = new UserAccount("owner", "password", true);
    UserAccount userAccount = new UserAccount("user", "password", false);

    private final Long requestingUserId = 0L;
    @Test
    void itShouldNotThrowWhenAnOwnerRequestsUsers() {
        when(repository.findById(requestingUserId)).thenReturn(Optional.of(ownerAccount));
        assertDoesNotThrow(() ->service.viewAllUsers());

    }
    @Test
    void itShouldThrowWhenANonOwnerRequestsUsers() {
        when(repository.findById(requestingUserId)).thenReturn(Optional.of(userAccount));
        final ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () ->service.viewAllUsers());
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
    }
    @Test
    void itShouldReturnAllUsersWhenOwnerRequestsUsers() {
//        when(repository.findById(requestingUserId)).thenReturn(Optional.of(ownerAccount));
        final ArrayList<UserAccount> allUserList = new ArrayList<>();
        allUserList.add(ownerAccount);
        allUserList.add(userAccount);
        when(repository.findAll()).thenReturn(allUserList);
        final Iterable<UserAccount> allUsers = service.viewAllUsers();
        assertEquals(allUserList, allUsers);
    }

    @Test
    void itShouldThrowUnAuthWhenNonOwnerRequestsDelete() {
        when(repository.findById(requestingUserId)).thenReturn(Optional.of(userAccount));
        final ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                ()->service.deleteUser(requestingUserId));

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
    }
    @Test
    void itShouldDeleteUserWhenOwnerRequestsDelete() {
        final Long idToBeDeleted = 1L;
        when(service.getOwner(requestingUserId)).thenReturn(Optional.of(ownerAccount));
        //doNothing().when(service).getOwner(requestingUserId);
        //doNothing().when(service).ownerCheck(requestingUserToken);
        service.deleteUser(idToBeDeleted);
        verify(repository).deleteById(idToBeDeleted);
    }
}

