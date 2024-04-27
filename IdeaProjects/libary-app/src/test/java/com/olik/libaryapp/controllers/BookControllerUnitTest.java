package com.olik.libaryapp.controllers;

import com.olik.libaryapp.controllers.BookController;
import com.olik.libaryapp.models.BookMdl;
import com.olik.libaryapp.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void testReturnBook() throws Exception {
        String bookName = "Test Book";

        mockMvc.perform(post("/books/return/" + bookName))
                .andExpect(status().isOk());

        verify(bookService, times(1)).returnBook(bookName);
    }
}