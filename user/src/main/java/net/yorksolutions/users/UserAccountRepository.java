package net.yorksolutions.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

    Optional<UserAccount> findByUsernameAndPassword(String username, String password);

    Optional<UserAccount> findByUsername(String username);

    Optional<UserAccount> findByIdAndIsOwner(Long id, boolean isOwner);

    Optional<UserAccount> findByIsOwner(boolean isOwner);
}
