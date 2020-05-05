package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.library.model.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("Поиск книги по названию")
    @Test
    void findBookByNameTest() {
        List<Book> books = bookRepository.findByName("Тест имя");
        assertThat(0).isEqualTo(books.size());
        books = bookRepository.findByName("книга1");
        assertThat(1).isEqualTo(books.size());
    }

}