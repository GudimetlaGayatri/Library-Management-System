
package com.example.library.model;

import jakarta.persistence.*;

@Entity
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bookId;
  private String title;
  private String author;
  private String category;
  private boolean availability;

  // getters and setters
  public Long getBookId() { return bookId; }
  public void setBookId(Long bookId) { this.bookId = bookId; }
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public String getAuthor() { return author; }
  public void setAuthor(String author) { this.author = author; }
  public String getCategory() { return category; }
  public void setCategory(String category) { this.category = category; }
  public boolean isAvailability() { return availability; }
  public void setAvailability(boolean availability) { this.availability = availability; }
}
