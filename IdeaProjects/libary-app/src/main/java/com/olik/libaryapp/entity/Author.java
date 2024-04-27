package com.olik.libaryapp.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Author extends Base{
    private String name;
    private String biography;
}
