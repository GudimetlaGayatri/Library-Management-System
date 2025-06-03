
package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.Transaction;
import com.example.library.model.User;
import com.example.library.repository.BookRepository;
import com.example.library.repository.TransactionRepository;
import com.example.library.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

  private final TransactionRepository transactionRepository;
  private final BookRepository bookRepository;
  private final UserRepository userRepository;

  public TransactionController(TransactionRepository transactionRepository,
                               BookRepository bookRepository,
                               UserRepository userRepository) {
    this.transactionRepository = transactionRepository;
    this.bookRepository = bookRepository;
    this.userRepository = userRepository;
  }

  @PostMapping("/issue")
  public Transaction issueBook(@RequestParam Long bookId, @RequestParam Long userId) {
    Book book = bookRepository.findById(bookId).orElseThrow();
    User user = userRepository.findById(userId).orElseThrow();
    if (!book.isAvailability()) throw new RuntimeException("Book not available");

    book.setAvailability(false);
    bookRepository.save(book);

    Transaction t = new Transaction();
    t.setBook(book);
    t.setUser(user);
    t.setIssueDate(LocalDate.now());
    t.setStatus("ISSUED");
    return transactionRepository.save(t);
  }

  @PostMapping("/return")
  public Transaction returnBook(@RequestParam Long transactionId) {
    Transaction t = transactionRepository.findById(transactionId).orElseThrow();
    if (!"ISSUED".equals(t.getStatus())) throw new RuntimeException("Book not issued");

    t.setReturnDate(LocalDate.now());
    t.setStatus("RETURNED");

    Book book = t.getBook();
    book.setAvailability(true);
    bookRepository.save(book);

    return transactionRepository.save(t);
  }

  @GetMapping("/user/{userId}")
  public List<Transaction> getUserTransactions(@PathVariable Long userId) {
    User user = userRepository.findById(userId).orElseThrow();
    return transactionRepository.findByUser(user);
  }

  @GetMapping("/overdue")
  public List<Transaction> getOverdueBooks() {
    return transactionRepository.findAll().stream()
        .filter(t -> "ISSUED".equals(t.getStatus()) && t.getIssueDate().plusDays(14).isBefore(LocalDate.now()))
        .toList();
  }
}

