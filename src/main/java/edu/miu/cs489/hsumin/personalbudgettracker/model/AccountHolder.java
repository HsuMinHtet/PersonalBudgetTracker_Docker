package edu.miu.cs489.hsumin.personalbudgettracker.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name="accountHolders")
@Getter
@Setter
@NoArgsConstructor
public class AccountHolder extends User{
    @OneToMany(mappedBy = "accountHolder")
    private List<Transaction> transactions= new ArrayList<>();
    @OneToMany(mappedBy = "accountHolder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categories = new ArrayList<>();
}
