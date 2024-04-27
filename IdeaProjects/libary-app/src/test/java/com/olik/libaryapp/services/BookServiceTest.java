package com.olik.libaryapp.services;

import com.olik.libaryapp.entity.Book;
import com.olik.libaryapp.enums.Bookstatus;
import com.olik.libaryapp.repository.BookRepo;
import com.olik.libaryapp.repository.RentalRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookServiceTest {

    @Mock
    BookRepo bookRepo;

    @Mock
    RentalService rentalService;

    @Mock
    RentalRepo rentalRepo;

    @InjectMocks
    BookService bookService;

    @Test
    public void rentBookValidBookAndUser() throws Exception {
        //mocking required methods which will be used in the actual method
        Mockito.when(bookRepo.findBytitle("ABCD")).thenReturn(Optional.of(new Book()));
        Mockito.when(rentalService.bookIsAvailable(Mockito.any())).thenReturn(true);
        Mockito.when(bookRepo.save(Mockito.any())).thenReturn(new Book());

        Book book = bookService.rentBook("SomeBookTitle", "SomeUserName");

        assertEquals(Bookstatus.RENTED, book.getStatus());
    }

    @Test
    public void rentBookInvalidBook() {
        Mockito.when(bookRepo.findBytitle("InvalidBookTitle")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> bookService.rentBook("InvalidBookTitle", "SomeUserName"));
    }

    @Test
    public void rentBookBookNotAvailable() {
        Mockito.when(bookRepo.findBytitle("UnavailableBookTitle")).thenReturn(Optional.of(new Book()));
        Mockito.when(rentalService.bookIsAvailable(Mockito.any())).thenReturn(false);

        assertThrows(Exception.class, () -> bookService.rentBook("UnavailableBookTitle", "SomeUserName"));
    }
}