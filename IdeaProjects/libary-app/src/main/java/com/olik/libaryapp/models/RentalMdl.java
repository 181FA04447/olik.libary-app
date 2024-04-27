package com.olik.libaryapp.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
public class RentalMdl {
    private UUID bookId;
    private String renterName;
    private Date rentDate;
}
