
package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

  private final BookRepository bookRepository;

  public BookController(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @GetMapping
  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  @PostMapping
  public Book addBook(@RequestBody Book book) {
    book.setAvailability(true);
    return bookRepository.save(book);
  }

  @PutMapping("/{id}")
  public Book updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
    Book book = bookRepository.findById(id).orElseThrow();
    book.setTitle(bookDetails.getTitle());
    book.setAuthor(bookDetails.getAuthor());
    book.setCategory(bookDetails.getCategory());
    book.setAvailability(bookDetails.isAvailability());
    return bookRepository.save(book);
  }

  @DeleteMapping("/{id}")
  public void deleteBook(@PathVariable Long id) {
    bookRepository.deleteById(id);
  }

  @GetMapping("/search")
  public List<Book> searchBooks(@RequestParam(required = false) String title,
                                @RequestParam(required = false) String author,
                                @RequestParam(required = false) String category) {
    if(title != null) return bookRepository.findByTitleContainingIgnoreCase(title);
    if(author != null) return bookRepository.findByAuthorContainingIgnoreCase(author);
    if(category != null) return bookRepository.findByCategoryContainingIgnoreCase(category);
    return bookRepository.findAll();
  }
}
