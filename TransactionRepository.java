
package com.example.library.repository;

import com.example.library.model.Transaction;
import com.example.library.model.User;
import com.example.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  List<Transaction> findByUser(User user);
  List<Transaction> findByBook(Book book);
  List<Transaction> findByStatus(String status);
}
