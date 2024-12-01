package edu.miu.cs489.hsumin.personalbudgettracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name="categories")
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;
    @OneToMany(mappedBy = "category")
    private List<Transaction> transactions;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_holder_id", nullable = false)
    private AccountHolder accountHolder;
    @Embedded
    private AuditData auditData;

}
