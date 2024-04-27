package com.olik.libaryapp.controllers;

import com.olik.libaryapp.models.BookMdl;
import com.olik.libaryapp.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void deleteBook_whenBookExists_returnStatus200() throws Exception {
        UUID bookId = UUID.randomUUID();
        doReturn(true).when(bookService).deleteBook(any());

        mockMvc.perform(delete("/books/" + bookId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBook_whenBookDoesNotExist_returnStatus404() throws Exception {
        UUID bookId = UUID.randomUUID();
        doReturn(false).when(bookService).deleteBook(any());

        mockMvc.perform(delete("/books/" + bookId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}