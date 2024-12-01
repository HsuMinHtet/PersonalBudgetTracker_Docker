package edu.miu.cs489.hsumin.personalbudgettracker.repository;

import edu.miu.cs489.hsumin.personalbudgettracker.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Integer> {
}
