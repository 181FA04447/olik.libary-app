package com.olik.libaryapp.entity;
import com.olik.libaryapp.enums.Bookstatus;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Book extends Base {
    private String title;
    private String author;
    private String isbn;
    private int publiciationyear;
    private Bookstatus status;
}
