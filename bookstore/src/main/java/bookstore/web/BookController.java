//Add a new controller called BookController which handle get request to the path /index

package bookstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import bookstore.domain.Book;
import bookstore.domain.BookRepository;

@Controller 
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/index")
    public String showIndex(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "index"; // index.html
    }
    @GetMapping("/one")
    public String showOneBook(Model model) {
        Book book = new Book("Harry Potter", "J.K. Rowling", 1997, "123456", 19.99);
        model.addAttribute("book", book);
        return "one";
    }
}
