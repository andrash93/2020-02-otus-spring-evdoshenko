package ru.mango.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.mango.server.data.model.User;

public interface UserRepository extends MongoRepository<User, String> {
}
