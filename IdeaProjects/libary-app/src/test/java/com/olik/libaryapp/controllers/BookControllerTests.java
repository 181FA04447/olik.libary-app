package com.olik.libaryapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olik.libaryapp.models.BookMdl;
import com.olik.libaryapp.services.BookService;
import com.olik.libaryapp.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.UUID;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
public class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;
   
    @Test
    public void addBook_shouldReturnNoContent_whenBookAddedSuccessfully() throws Exception {
        BookMdl bookMdl = new BookMdl();
        bookMdl.setTitle("SpringGuide");
        bookMdl.setAuthor("Craig Walls");
        bookMdl.setIsbn("9781491906340");

        Mockito.when(bookService.addBook(bookMdl)).thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bookMdl)))
                .andExpect(status().isNoContent());

        Mockito.verify(bookService).addBook(bookMdl);
    }

    @Test
    public void addBook_shouldReturnBadRequest_whenRequestBodyIsInvalid() throws Exception {
        BookMdl bookMdl = new BookMdl();

        Mockito.when(bookService.addBook(bookMdl)).thenReturn(ResponseEntity.badRequest().build());

        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bookMdl)))
                .andExpect(status().isBadRequest());

        Mockito.verify(bookService).addBook(bookMdl);
    }
}