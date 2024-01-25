package ru.fa.books;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class BooksController {
    private final BookRepository bookRepository;

    public BooksController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public String booksView(@RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "publishing", required = false) String publishing,
                            @RequestParam(value = "student", required = false) String student,
                            @RequestParam(value = "order", required = false) String order,
                            Model model) {
        name = (name != null) && (name.isEmpty()) ? null : name;
        publishing = (publishing != null) && (publishing.isEmpty()) ? null : publishing;
        student = (student != null) && (student.isEmpty()) ? null : student;
        Sort.Direction direction = Sort.Direction.DESC;
        if (order != null && order.equals("asc")) {
            direction = Sort.Direction.ASC;
        }
        model.addAttribute("count", bookRepository.count());
        model.addAttribute(
                "books",
                bookRepository.filter(name, publishing, student, Sort.by(direction, "issueDate"))
        );
        return "books/books";
    }

    @GetMapping("/{id}")
    public String bookView(@PathVariable("id") int id,
                           Model model) {
        model.addAttribute("exists", true);
        bookRepository.findById(id).ifPresent(book -> model.addAttribute("book", book));
        return "books/book";
    }

    @GetMapping("/new")
    public String createBookView(@ModelAttribute("book") Book book, Model model) {

        model.addAttribute("exists", false);
        return "books/book";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") Book book) {
        bookRepository.save(book);
        return "redirect:/";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id,
                             @ModelAttribute("book") Book book) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book bookInDatabase = bookOptional.get();
            bookInDatabase.setName(book.getName());
            bookInDatabase.setPublishing(book.getPublishing());
            bookInDatabase.setStudentName(book.getStudentName());
            bookInDatabase.setIssueDate(book.getIssueDate());
            bookInDatabase.setReturnDate(book.getReturnDate());
            bookRepository.save(bookInDatabase);
        }
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookRepository.deleteById(id);
        return "redirect:/";
    }
}
