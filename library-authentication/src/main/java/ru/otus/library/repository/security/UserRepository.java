package ru.otus.library.repository.security;

import org.springframework.data.repository.CrudRepository;
import ru.otus.library.data.security.UserEntity;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserName(String s);

}
