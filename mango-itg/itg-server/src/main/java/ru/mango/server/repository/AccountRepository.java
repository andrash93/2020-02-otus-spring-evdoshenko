package ru.mango.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.mango.server.data.model.Account;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {

    Optional<Account> findByAdminUserId(Integer userId);

    Optional<Account> findByGroupCharId(Long chatId);

    Optional<Account> findByVpbxApiKey(String vpbxApiKey);
}
