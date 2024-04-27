package com.olik.libaryapp.controllers;

import com.olik.libaryapp.models.BookMdl;
import com.olik.libaryapp.services.BookService;
import com.olik.libaryapp.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    BookService bookService;
    //bookMdl

    @PostMapping
    public ResponseEntity<Void> addBook(@RequestBody BookMdl book) {
        return bookService.addBook(book);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/author/{author}")
    public List<Book> getBooksByAuthor(@PathVariable String author) {
        return bookService.getBooksByAuthor(author);
    }

    @GetMapping("/available")
    public List<Book> getAvailableBooksForRent() {
        return bookService.getAvailableBooksForRent();
    }

    @GetMapping("/rented")
    public List<Book> getCurrentlyRentedBooks() {
        return bookService.getCurrentlyRentedBooks();
    }

    @PutMapping()
    public ResponseEntity<Book> updateBook(@RequestBody Book updatedBook) {
        Book newBook = bookService.updateBook(updatedBook);
        return new ResponseEntity<>(newBook, HttpStatus.OK);
    }

    @PostMapping("/rent/{bookName}")
    public Book rentBook(@PathVariable String bookName, @RequestParam String userName) throws Exception {
        return bookService.rentBook(bookName, userName);
    }

    @PostMapping("/return/{bookName}")
    public Book returnBook(@PathVariable String bookName) {
        return bookService.returnBook(bookName);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        boolean deleted = bookService.deleteBook(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
