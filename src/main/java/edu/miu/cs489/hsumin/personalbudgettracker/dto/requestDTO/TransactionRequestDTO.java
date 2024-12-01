package edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO;

import edu.miu.cs489.hsumin.personalbudgettracker.model.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

public record TransactionRequestDTO(

         @NotNull(message = "Transaction Amount is required")
         Double amount,

         @CreatedDate
         LocalDate transactionDate,

         String description,

         @NotNull(message = "Transaction Type is required")
         @Enumerated(EnumType.STRING)
         TransactionType type,

         Integer accountHolderId,

         Long categoryId

) {}
