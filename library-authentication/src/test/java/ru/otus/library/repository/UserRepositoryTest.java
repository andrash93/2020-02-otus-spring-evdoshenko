package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.library.data.library.model.Author;
import ru.otus.library.data.security.UserEntity;
import ru.otus.library.repository.security.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("Поиск юзеров по имени")
    @Test
    void findAuthorByNameTest() {
        Optional<UserEntity> admin = userRepository.findByUserName("admin");
        assertThat(true).isEqualTo(admin.isPresent());
    }

}