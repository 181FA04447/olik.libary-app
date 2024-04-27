package com.olik.libaryapp.services;

import com.olik.libaryapp.entity.Book;
import com.olik.libaryapp.entity.Rental;
import com.olik.libaryapp.enums.Bookstatus;
import com.olik.libaryapp.enums.RentalStatus;
import com.olik.libaryapp.models.BookMdl;
import com.olik.libaryapp.models.RentalMdl;
import com.olik.libaryapp.repository.BookRepo;
import com.olik.libaryapp.repository.RentalRepo;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    @Autowired
    BookRepo bookRepo;
    @Autowired
    RentalService rentalService;
    @Autowired
    RentalRepo rentalRepo;

    public ResponseEntity<Void> addBook(BookMdl book) {
        Book b = new Book();
        b.setTitle(book.getTitle());
        b.setIsbn(book.getIsbn());
        b.setAuthor(book.getAuthor());
        b.setStatus(Bookstatus.AVAILABLE);
        b.setPubliciationyear(book.getPubliciationyear());
        bookRepo.save(b);
        return new ResponseEntity<>(HttpStatusCode.valueOf(201));
    }

    public List<Book> getBooksByAuthor(String author) {
        List<Book> books = bookRepo.findByAuthor(author);
        return books;
    }

    public List<Book> getAvailableBooksForRent() {
        return bookRepo.findBystatus(Bookstatus.AVAILABLE);
    }

    public List<Book> getCurrentlyRentedBooks() {
        return bookRepo.findBystatus(Bookstatus.RENTED);
    }

    public Book updateBook(Book updatedBook) {
        if (bookRepo.existsById(updatedBook.getId())) {
            return bookRepo.save(updatedBook);
            // Book updated successfully
        } else {
            throw new RuntimeException("Book not found");// Book not found
        }
    }

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Book rentBook(String Bookname, String userName) throws Exception {
        Optional<Book> optionalBook =bookRepo.findBytitle(Bookname);
        if (optionalBook.isEmpty())
            throw new RuntimeException("Book not found");
        Book b = optionalBook.get();
        Boolean isBookAvailable = rentalService.bookIsAvailable(b.getId());
        if (isBookAvailable) {
            RentalMdl rentalMdl = new RentalMdl();
            rentalMdl.setBookId(b.getId());
            rentalMdl.setRentDate(new Date());
            rentalMdl.setRenterName(userName);
            rentalService.postarental(rentalMdl);

            b.setStatus(Bookstatus.RENTED);
            bookRepo.save(b);
            return b;
        } else {
            throw new ObjectNotFoundException(b.getId(), "Book is Already Rented");
        }

    }

    public boolean deleteBook(UUID id) {
        Optional<Book> bookOptional = bookRepo.findById(id);
        if (bookOptional.isEmpty())
            return false;
        else {
            bookRepo.deleteById(id);
        }
        return true;
    }

    public Book returnBook(String bookName) {
        //no need to check whether the book exists or not if returning mrans he already owe it
        //get book id
        Optional< Book>optionalBook= bookRepo.findBytitle(bookName);
        Book b=optionalBook.get();
        Optional<Rental> rentalOptional = rentalRepo.findByBookId(b.getId());
        if (rentalOptional.isEmpty())
            throw new RuntimeException("Rental id not found");
        Rental rental = rentalOptional.get();
        rental.setRentalStatus(RentalStatus.RETURNED);
        b.setStatus(Bookstatus.AVAILABLE);
        rentalRepo.save(rental);
        bookRepo.save(b);
        return b;
    }
}
