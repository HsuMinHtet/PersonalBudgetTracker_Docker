package edu.miu.cs489.hsumin.personalbudgettracker.repository;

import edu.miu.cs489.hsumin.personalbudgettracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByAccountHolderId(Integer accountHolderId);
}
