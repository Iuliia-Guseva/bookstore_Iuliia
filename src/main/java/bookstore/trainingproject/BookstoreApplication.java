package bookstore.trainingproject;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import bookstore.domain.Book;
import bookstore.domain.BookRepository;
import bookstore.domain.Category;
import bookstore.domain.CategoryRepository;
import bookstore.domain.User;
import bookstore.domain.UserRepository;

@SpringBootApplication(scanBasePackages = "bookstore")
@EntityScan(basePackages = "bookstore.domain")
@EnableJpaRepositories(basePackages = "bookstore.domain")
public class BookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(
            BookRepository repository,
            CategoryRepository categoryRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            addUserIfMissing(userRepository, passwordEncoder, "user", "user", "user@example.com", "USER");
            addUserIfMissing(userRepository, passwordEncoder, "admin", "admin", "admin@example.com", "ADMIN");

            Category fantasy = categoryRepository.save(new Category("Fantasy"));
            Category adventure = categoryRepository.save(new Category("Adventure"));

            Book harryPotter = new Book(
                "Harry Potter",
                "J.K. Rowling",
                1997,
                "123456",
                new BigDecimal("19.99")
            );
            harryPotter.setCategory(fantasy);
            repository.save(harryPotter);

            Book theHobbit = new Book(
                "The Hobbit",
                "J.R.R. Tolkien",
                1937,
                "654321",
                new BigDecimal("15.99")
            );
            theHobbit.setCategory(adventure);
            repository.save(theHobbit);
        };
    }

    private void addUserIfMissing(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            String username,
            String rawPassword,
            String email,
            String role) {
        if (userRepository.findByUsername(username).isEmpty()) {
            userRepository.save(new User(username, passwordEncoder.encode(rawPassword), email, role));
        }
    }
}
