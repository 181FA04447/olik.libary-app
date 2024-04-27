package com.olik.libaryapp.services;

import com.olik.libaryapp.repository.AuthorRepo;
import com.olik.libaryapp.models.authorMdl;
import com.olik.libaryapp.entity.Author;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Getter
@Setter
public class AuthorService {
    @Autowired
    AuthorRepo authorRepo;

    public Author deleteAuthor(UUID id) throws RuntimeException {
        Optional<Author>optionalAuthor=authorRepo.findById(id);
        if(optionalAuthor.isEmpty())
            throw  new RuntimeException("Author not found");
        else{
            authorRepo.deleteById(id);
        }
        return optionalAuthor.get();

    }

    public Author postauthor(authorMdl author)
    {
        Author a=new Author();
        a.setBiography(author.getBiography());
        a.setName(author.getName());
        authorRepo.save(a);
        return a;
    }
    public List<Author> getAllAuthors()
    {
        return  authorRepo.findAll();
        //return  authors;
    }

    public boolean updateAuthor(UUID id, Author updatedAuthor) {
        updatedAuthor.setId(id);
        if (authorRepo.existsById(id)) {
            authorRepo.save(updatedAuthor);
            return true; // Book updated successfully
        } else {
            return false; // Book not found
        }
    }

    public boolean patchAuthor(UUID id, Author updatedBook) {
        if (authorRepo.existsById(id)) {
            Author existingBook = authorRepo.findById(id).get();
            // Update only non-null fields
            if (updatedBook.getName() != null) {
                existingBook.setName(updatedBook.getName());
            }
            if (updatedBook.getBiography() != null) {
                existingBook.setBiography(updatedBook.getBiography());
            }
            // Update other fields as needed
            authorRepo.save(existingBook);
            return true; // Book patched successfully
        } else {
            return false; // Book not found
        }
    }
}
