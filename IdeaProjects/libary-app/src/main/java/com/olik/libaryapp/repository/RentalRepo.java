package com.olik.libaryapp.repository;
import com.olik.libaryapp.enums.RentalStatus;
import com.olik.libaryapp.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface RentalRepo  extends JpaRepository<Rental, UUID> {

    @Query(value = "SELECT r from Rental r where r.returnDate>CURRENT_DATE AND r.rentalStatus=:rentalStatus")
    List<Rental> getByDueDate(RentalStatus rentalStatus);

    Optional<Rental> findByBookId(UUID bookId);
}
