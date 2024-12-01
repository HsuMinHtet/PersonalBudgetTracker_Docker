package edu.miu.cs489.hsumin.personalbudgettracker.repository;

import edu.miu.cs489.hsumin.personalbudgettracker.model.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountHolderRepository extends JpaRepository<AccountHolder,Integer> {
    Optional<AccountHolder> findByEmail(String email);
}
