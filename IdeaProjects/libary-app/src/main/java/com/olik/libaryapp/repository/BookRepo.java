package com.olik.libaryapp.repository;

import com.olik.libaryapp.enums.Bookstatus;
import com.olik.libaryapp.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepo extends JpaRepository<Book, UUID> {
    List<Book> findByAuthor(String author);

    List<Book> findBystatus(Bookstatus bookstatus);

   Optional< Book> findBytitle(String bookname);
}
