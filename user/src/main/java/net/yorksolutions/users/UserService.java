package net.yorksolutions.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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


    public Iterable<UserAccount> viewAllUsers() {
        return repository.findAll();
    }
    public void deleteUser(Long idToBeDeleted) {
        repository.deleteById(idToBeDeleted);
    }

    public Optional<UserAccount> getOwner(Long userId) {

        //edit example
//        var user = repository.findById(userId).get();
//        user.username = "stan";
//        repository.save(user);
        return repository.findByIdAndIsOwner(userId, true);

    }

    public void setRepository(UserAccountRepository repository) {
        this.repository = repository;
    }

}
