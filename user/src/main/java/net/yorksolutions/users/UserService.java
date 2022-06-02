package net.yorksolutions.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;


@Service
public class UserService {



    private UserAccountRepository repository;

    //Spring
    @Autowired
    public UserService(@NonNull UserAccountRepository repository) {
        this.repository = repository;
    }


    public Iterable<UserAccount> viewAllUsers(UUID requestingUserToken) {
        ownerCheck(requestingUserToken);
        return repository.findAll();
    }
    public void deleteUser(UUID requestingUserToken, Long idToBeDeleted) {
        ownerCheck(requestingUserToken);
        repository.deleteById(idToBeDeleted);
    }

     void ownerCheck(UUID requestingUserToken) {
        var isOwner = repository.isOwner(requestingUserToken);

        if (isOwner.equals(false)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
    public void setRepository(UserAccountRepository repository) {
        this.repository = repository;
    }

}
