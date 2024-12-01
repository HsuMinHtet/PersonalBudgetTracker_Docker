package edu.miu.cs489.hsumin.personalbudgettracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer address_id;
    private String city;
    private String street;
    private String number;
    private Integer postalCode;
    private String country;
    private String state;
}
