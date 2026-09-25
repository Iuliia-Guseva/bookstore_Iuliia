package bookstore.web;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bookstore.domain.Book;
import bookstore.domain.BookRepository;
import bookstore.domain.CategoryRepository;

@Controller 
public class BookController {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookController(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/bookstore")
    public String showIndex(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "Bookstore";
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public @ResponseBody List<Book> bookListRest() {
        return (List<Book>) bookRepository.findAll();
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Book> findBookRest(@PathVariable("id") Long bookId) {
        return bookRepository.findById(bookId);
    }

    @GetMapping("/add")
    public String addBook(Model model){
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryRepository.findAll());
        return "addBook";
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow();
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryRepository.findAll());
        return "editBook";
    }

    @PostMapping("/save")
    public String save(Book book, @RequestParam Long categoryId){
        if (hasBookData(book)) {
            book.setCategory(categoryRepository.findById(categoryId).orElseThrow());
            bookRepository.save(book);
        }
        return "redirect:/bookstore";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, Book book, @RequestParam Long categoryId) {
        if (hasBookData(book)) {
            book.setId(id);
            book.setCategory(categoryRepository.findById(categoryId).orElseThrow());
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
                || (book.getPrice() != null && book.getPrice().compareTo(BigDecimal.ZERO) != 0);
    }
}
