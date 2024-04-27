package com.olik.libaryapp.entity;

import com.olik.libaryapp.enums.RentalStatus;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Rental extends Base {
    private UUID bookId;
    private String renterName;
    private Date rentDate;
    private LocalDate returnDate;
    private RentalStatus rentalStatus;
}
