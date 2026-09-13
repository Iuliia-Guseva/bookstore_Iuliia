package bookstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import bookstore.domain.Book;
import bookstore.domain.BookRepository;

@Controller 
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/bookstore")
    public String showIndex(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "Bookstore";
    }

    @GetMapping("/add")
    public String addBook(Model model){
        model.addAttribute("book", new Book());
        return "addBook";
    }

    @PostMapping("/save")
    public String save(Book book){
        if (hasBookData(book)) {
            bookRepository.save(book);
        }
        return "redirect:/bookstore";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return "redirect:/bookstore";
    }

    private boolean hasBookData(Book book) {
        return (book.getTitle() != null && !book.getTitle().isBlank())
                || (book.getAuthor() != null && !book.getAuthor().isBlank())
                || book.getPublicationYear() != null
                || (book.getIsbn() != null && !book.getIsbn().isBlank())
                || book.getPrice() != 0.0;
    }
}
