package bookstore.trainingproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

import bookstore.domain.Book;
import bookstore.domain.BookRepository;

@SpringBootApplication(scanBasePackages = "bookstore")
@EntityScan(basePackages = "bookstore.domain")
@EnableJpaRepositories(basePackages = "bookstore.domain")
public class BookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(BookRepository repository) {
        return args -> {
            repository.save(new Book(
                "Harry Potter",
                "J.K. Rowling",
                1997,
                "123456",
                19.99
            ));

            repository.save(new Book(
                "The Hobbit",
                "J.R.R. Tolkien",
                1937,
                "654321",
                15.99
            ));
        };
    }
}
