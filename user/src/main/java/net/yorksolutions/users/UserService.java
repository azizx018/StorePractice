package net.yorksolutions.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserAccountRepository repository;

    //Spring
    @Autowired
    public UserService(@NonNull UserAccountRepository repository) {
        this.repository = repository;
    }


    public Iterable<UserAccount> viewAllUsers(Long requestingUserId) {
        ownerCheck(requestingUserId);
        return repository.findAll();
    }
    public void deleteUser(Long requestingUserId, Long idToBeDeleted) {
        ownerCheck(requestingUserId);
        repository.deleteById(idToBeDeleted);
    }

     void ownerCheck(Long requestingUserId) {
        var isOwner = repository.isOwner(requestingUserId);

        if (isOwner.equals(false)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

}
