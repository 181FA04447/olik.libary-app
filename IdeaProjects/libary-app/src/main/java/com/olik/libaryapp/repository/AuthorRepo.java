package com.olik.libaryapp.repository;

import com.olik.libaryapp.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepo extends JpaRepository<Author, UUID> {
}
