package com.olik.libaryapp.controllers;

import com.olik.libaryapp.models.authorMdl;
import com.olik.libaryapp.services.AuthorService;
import com.olik.libaryapp.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/author")

public class AuthorController {
    @Autowired
    AuthorService authorService;
    @PostMapping
    public ResponseEntity<Void> postAuthor(@RequestBody authorMdl author)
    {
          authorService.postauthor(author);
          return new ResponseEntity<>(HttpStatusCode.valueOf(201));
    }
    @GetMapping
    public List<Author> getAllAuthors()
    {
        return authorService.getAllAuthors();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAuthor(@PathVariable UUID id, @RequestBody Author updatedAuthor) {
        boolean updated = authorService.updateAuthor(id, updatedAuthor);
        if (updated) {
            return ResponseEntity.ok().build(); // Book updated successfully
        } else {
            return ResponseEntity.notFound().build(); // Book not found
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchAuthor(@PathVariable UUID id, @RequestBody Author updatedAuthor) {
        boolean patched = authorService.patchAuthor(id, updatedAuthor);
        if (patched) {
            return ResponseEntity.ok().build(); // Book patched successfully
        } else {
            return ResponseEntity.notFound().build(); // Book not found
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable UUID id){
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));

    }

}
