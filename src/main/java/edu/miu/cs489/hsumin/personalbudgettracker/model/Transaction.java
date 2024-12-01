package edu.miu.cs489.hsumin.personalbudgettracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity(name="transactions")
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Double amount;
    @CreationTimestamp
    private LocalDate transactionDate;
    private String description;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_holder_id")
    private AccountHolder accountHolder;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="category_id")
    private Category category;

    @Embedded
    private AuditData auditData;

}
