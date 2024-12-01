package edu.miu.cs489.hsumin.personalbudgettracker.service;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.TransactionRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.TransactionResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.model.TransactionType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionService {
    Optional<TransactionResponseDTO> registerTransaction(TransactionRequestDTO transactionRequestDTO);

    Optional<TransactionResponseDTO> updateTransaction(Long id, TransactionRequestDTO transactionRequestDTO);

    void deleteTransaction(Long id);

    Optional<TransactionResponseDTO> findByTransactionID(Long id);

    List<TransactionResponseDTO> findAllTransactions(Integer accountHolder_id);

    List<TransactionResponseDTO> searchTransactions(Integer accountHolder_id,LocalDate transactionDate, Double amount, TransactionType transactionType, String description, Long categoryId);
}
