package com.olik.libaryapp.controllers;

import com.olik.libaryapp.models.RentalMdl;
import com.olik.libaryapp.services.RentalService;
import com.olik.libaryapp.entity.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {
    @Autowired
    RentalService rentalService;
    @GetMapping("/overdue-books")
    public List<Rental> overduebooks(@RequestParam Date date){
          return rentalService.overDueBooks();
    }
    @PostMapping
    public ResponseEntity<Void> postarental(@RequestBody RentalMdl rentalMdl)
    {
             rentalService.postarental(rentalMdl);
        return new ResponseEntity<>(HttpStatus.valueOf(201));
    }
    @GetMapping
    public List<Rental> getAllRentals() {
       return rentalService.getAllRentals();
    }
}
