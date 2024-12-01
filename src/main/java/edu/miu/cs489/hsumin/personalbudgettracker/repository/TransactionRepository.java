package edu.miu.cs489.hsumin.personalbudgettracker.repository;

import edu.miu.cs489.hsumin.personalbudgettracker.model.Category;
import edu.miu.cs489.hsumin.personalbudgettracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findALLByAccountHolder_Id(Integer accountHolderId);

    Boolean existsByCategory(Category category);
}
