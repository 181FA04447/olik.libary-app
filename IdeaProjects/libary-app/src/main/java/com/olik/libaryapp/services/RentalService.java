package com.olik.libaryapp.services;

import com.olik.libaryapp.repository.RentalRepo;
import com.olik.libaryapp.models.RentalMdl;
import com.olik.libaryapp.enums.RentalStatus;
import com.olik.libaryapp.entity.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class RentalService {
    @Autowired
    private RentalRepo rentalRepo;
    public List<Rental> overDueBooks(){
          return rentalRepo.getByDueDate(RentalStatus.NOT_RETURNED);
    }
    public void postarental(RentalMdl rentalMdl) {
        Rental r=new Rental();
        r.setBookId(rentalMdl.getBookId());
        r.setRenterName(rentalMdl.getRenterName());
        r.setRentDate(rentalMdl.getRentDate());
        r.setRentalStatus(RentalStatus.NOT_RETURNED);
        LocalDate d= rentalMdl.getRentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        d=d.plusDays(14);
        r.setReturnDate(d);
        rentalRepo.save(r);
        //return new ResponseEntity<>(HttpStatus.valueOf(201));
    }
    public boolean bookIsAvailable(UUID bookId){
        Optional<Rental>rentalOptional=rentalRepo.findByBookId(bookId);
        if(rentalOptional.isPresent() && rentalOptional.get().getRentalStatus().equals(RentalStatus.NOT_RETURNED))
        {
            return false;
        }
        else return true;

    }

    public List<Rental> getAllRentals() {
        return rentalRepo.findAll();
    }

    //rentalservice-->bookservice
    //Renatal
}
