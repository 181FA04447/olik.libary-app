package com.olik.libaryapp.services;

import com.olik.libaryapp.entity.Book;
import com.olik.libaryapp.entity.Rental;
import com.olik.libaryapp.enums.Bookstatus;
import com.olik.libaryapp.enums.RentalStatus;
import com.olik.libaryapp.repository.BookRepo;
import com.olik.libaryapp.repository.RentalRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BookServiceTests {
    @Autowired
    BookService bookService;

    @MockBean
    BookRepo bookRepo;

    @MockBean
    RentalRepo rentalRepo;

    /**
     * Test for BookService.returnBook method
     * This test confirms that a book is returnable successfully.
     */
    @Test
    public void testReturnBookSuccessfully() {
        Book testBook = new Book();
        testBook.setId(UUID.randomUUID());
        testBook.setStatus(Bookstatus.RENTED);

        Rental testRental = new Rental();
        testRental.setRentalStatus(RentalStatus.RETURNED);
        testRental.setBookId(testBook.getId());

        Mockito.when(bookRepo.findBytitle("Test Book")).thenReturn(Optional.of(testBook));
        Mockito.when(rentalRepo.findByBookId(testBook.getId())).thenReturn(Optional.of(testRental));
        Mockito.when(bookRepo.save(Mockito.any())).thenReturn(testBook);

        Book returnedBook = bookService.returnBook("Test Book");

        assertEquals(Bookstatus.AVAILABLE, returnedBook.getStatus());
    }

    /**
     * Test for BookService.returnBook method
     * This test confirms that a RuntimeException is thrown when a non-existent book is returned.
     */
    @Test
    public void testReturnNonExistentBook() {
        Mockito.when(bookRepo.findBytitle("Non-Existent Book")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookService.returnBook("Non-Existent Book"));
    }

    /**
     * Test for BookService.returnBook method
     * This test confirms that a RuntimeException is thrown when an already returned book is returned again.
     */
    @Test
    public void testReturnAlreadyReturnedBook() {
        Book testBook = new Book();
        testBook.setId(UUID.randomUUID());
        testBook.setStatus(Bookstatus.AVAILABLE);

        Mockito.when(bookRepo.findBytitle("Test Book")).thenReturn(Optional.of(testBook));
        Mockito.when(rentalRepo.findByBookId(testBook.getId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookService.returnBook("Test Book"));
    }
}