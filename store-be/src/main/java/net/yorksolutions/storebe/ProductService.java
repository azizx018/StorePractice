package net.yorksolutions.storebe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private ProductAccountRepository repository;

    @Autowired
    public ProductService(@NonNull ProductAccountRepository repository) {
        this.repository = repository;
    }

    public Iterable<ProductAccount> viewAllProducts() {
        return repository.findAll();
    }
}

//get all
//add
//delete
//edit