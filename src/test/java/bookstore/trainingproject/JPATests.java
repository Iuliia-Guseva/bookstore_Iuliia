package bookstore.trainingproject;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import bookstore.domain.Book;
import bookstore.domain.BookRepository;
import bookstore.domain.Category;
import bookstore.domain.CategoryRepository;
import bookstore.domain.User;
import bookstore.domain.UserRepository;

@DataJpaTest
class JPATests {

    @TestConfiguration(proxyBeanMethods = false)
    static class JpaTestConfiguration {

        @Bean
        PasswordEncoder passwordEncoder() {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
    }

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void createsBook() {
        Category category = categoryRepository.save(new Category("TestCategory"));
        Book book = new Book("TestBook", "TestAuthor", 2024, "TEST-ISBN-001",
                BigDecimal.valueOf(9.99));
        book.setCategory(category);

        Book savedBook = bookRepository.save(book);

        assertThat(savedBook.getId()).isNotNull();
        assertThat(bookRepository.findById(savedBook.getId())).contains(savedBook);
    }

    @Test
    void deletesBook() {
        Category category = categoryRepository.save(new Category("TestCategory"));
        Book book = new Book("TestBook", "TestAuthor", 2024, "TEST-ISBN-001",
                BigDecimal.valueOf(9.99));
        book.setCategory(category);
        Long bookId = bookRepository.save(book).getId();

        bookRepository.deleteById(bookId);

        assertThat(bookRepository.findById(bookId)).isEmpty();
    }

    @Test
    void searchesBooksByTitle() {
        Category category = categoryRepository.save(new Category("TestCategory"));
        Book book = new Book("TestBook", "TestAuthor", 2024, "TEST-ISBN-001",
                BigDecimal.valueOf(9.99));
        book.setCategory(category);
        bookRepository.save(book);

        assertThat(bookRepository.findByTitle("TestBook"))
                .singleElement()
                .extracting(Book::getAuthor)
                .isEqualTo("TestAuthor");
    }

    @Test
    void createsCategory() {
        Category savedCategory = categoryRepository.save(new Category("TestCategory"));

        assertThat(savedCategory.getId()).isNotNull();
        assertThat(categoryRepository.findById(savedCategory.getId())).contains(savedCategory);
    }

    @Test
    void deletesCategory() {
        Long categoryId = categoryRepository.save(new Category("TestCategory")).getId();

        categoryRepository.deleteById(categoryId);

        assertThat(categoryRepository.findById(categoryId)).isEmpty();
    }

    @Test
    void searchesCategoriesByName() {
        categoryRepository.save(new Category("TestCategory"));

        assertThat(categoryRepository.findByName("TestCategory"))
                .singleElement()
                .extracting(Category::getName)
                .isEqualTo("TestCategory");
    }

    @Test
    void createsUser() {
        User savedUser = userRepository.save(new User("test-user", "test-password", "test@example.com", "TEST_USER"));

        assertThat(savedUser.getId()).isNotNull();
        assertThat(userRepository.findById(savedUser.getId())).contains(savedUser);
    }

    @Test
    void deletesUser() {
        Long userId = userRepository.save(new User("test-user", "test-password", "test@example.com", "TEST_USER"))
                .getId();

        userRepository.deleteById(userId);

        assertThat(userRepository.findById(userId)).isEmpty();
    }

    @Test
    void searchesUsersByUsername() {
        userRepository.save(new User("test-user", "test-password", "test@example.com", "TEST_USER"));

        assertThat(userRepository.findByUsername("test-user"))
                .get()
                .extracting(User::getEmail)
                .isEqualTo("test@example.com");
    }
}
