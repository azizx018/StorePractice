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
public class AuthorizationService {
    private UserAccountRepository repository;
    private HashMap<UUID, Long> tokenMap;

    //Spring
    @Autowired
    public AuthorizationService(@NonNull UserAccountRepository repository) {
        this.repository = repository;
        this.tokenMap = new HashMap<>();
    }
    //Mockito
    public AuthorizationService(@NonNull UserAccountRepository repository,HashMap<UUID, Long> tokenMap) {
        this.repository = repository;
        this.tokenMap = tokenMap;
    }



    public UUID login(String username, String password) {
        Optional<UserAccount> result = repository.findByUsernameAndPassword(username, password);

        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        final UUID token = UUID.randomUUID();
        var user = result.get();
        tokenMap.put(token, user.id);
        return token;

    }

    public void logout(UUID token) {
        if (tokenMap.containsKey(token)) {
            tokenMap.remove(token);
        }
    }
    public void registerCustomer(String username, String password) {
        if (repository.findByUsername(username).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        repository.save(new UserAccount(username, password, false));
    }
    public void registerOwner(String username, String password) {
        if (repository.findByUsername(username).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        repository.save(new UserAccount(username, password, true));
    }

    public void isAuthorized (UUID token) {
        if (tokenMap.containsKey(token)) {
            return;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

    }


}
