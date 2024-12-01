package edu.miu.cs489.hsumin.personalbudgettracker.repository;

import edu.miu.cs489.hsumin.personalbudgettracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);
}
