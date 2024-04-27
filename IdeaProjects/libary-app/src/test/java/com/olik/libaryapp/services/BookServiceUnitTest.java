package com.olik.libaryapp.services;

import com.olik.libaryapp.entity.Book;
import com.olik.libaryapp.enums.Bookstatus;
import com.olik.libaryapp.repository.BookRepo;
import com.olik.libaryapp.repository.RentalRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class BookServiceUnitTest {

    @Mock
    private BookRepo bookRepo;

    @Mock
    private RentalService rentalService;

    @Mock
    private RentalRepo rentalRepo;

    @InjectMocks
    private BookService bookService;

    @Test
    public void rentBookSuccessfully() throws Exception {
        // given
        String bookName = "The Book";
        String userName = "User";
        Book mockBook = new Book();
        mockBook.setStatus(Bookstatus.AVAILABLE);
        mockBook.setTitle(bookName);
        mockBook.setId(UUID.randomUUID());

        // when
        Mockito.when(bookRepo.findBytitle(bookName)).thenReturn(Optional.of(mockBook));
        Mockito.when(rentalService.bookIsAvailable(Mockito.any())).thenReturn(true);

        // then
        Book result = bookService.rentBook(bookName, userName);

        // verify
        Assertions.assertEquals(Bookstatus.RENTED, result.getStatus());
    }

    @Test
    public void rentBookFailureDueToBookNotFound() {
        // given
        String bookName = "The Book";
        String userName = "User";

        // when
        Mockito.when(bookRepo.findBytitle(bookName)).thenReturn(null);

        // then
        Assertions.assertThrows(RuntimeException.class, () -> bookService.rentBook(bookName, userName));
    }

    @Test
    public void rentBookFailureDueToBookAlreadyRented() {
        // given
        String bookName = "The Book";
        String userName = "User";
        Book mockBook = new Book();
        mockBook.setStatus(Bookstatus.RENTED);
        mockBook.setId(UUID.randomUUID());

        // when
        Mockito.when(bookRepo.findBytitle(bookName)).thenReturn(Optional.of(mockBook));
        Mockito.when(rentalService.bookIsAvailable(Mockito.any())).thenReturn(false);

        // then
        Assertions.assertThrows(Exception.class, () -> bookService.rentBook(bookName, userName));
    }
}