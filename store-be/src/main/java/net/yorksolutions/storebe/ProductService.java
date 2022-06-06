package net.yorksolutions.storebe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class ProductService {

    private final RestTemplate rest;

    public ProductService() {
        rest = new RestTemplate();
    }


    @Autowired
    public ProductService(@NonNull ProductAccountRepository repository) {
        this.rest = new RestTemplate();
        this.repository = repository;
    }
    private ProductAccountRepository repository;

    public void checkAuthorized(UUID token) {
        String url = "http://localhost:8081/isAuthorized?token=" + token;
        final ResponseEntity<UUID> response = rest.getForEntity(url, UUID.class);

        switch(response.getStatusCode()) {
            case OK:
                return;

            case UNAUTHORIZED:
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

            default:
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public Iterable<ProductAccount> viewAllProducts(UUID token) {
        checkAuthorized(token);
        return repository.findAll();
    }
}

//get all
//add
//delete
//edit