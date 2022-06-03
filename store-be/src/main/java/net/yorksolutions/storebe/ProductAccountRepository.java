package net.yorksolutions.storebe;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProductAccountRepository extends CrudRepository<ProductAccount, Long>{

        Optional<ProductAccount> findById(Long id);

}
