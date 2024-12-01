package edu.miu.cs489.hsumin.personalbudgettracker.controller;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.TransactionRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.AccountHolderResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.CategoryResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.TransactionResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.model.Role;
import edu.miu.cs489.hsumin.personalbudgettracker.model.TransactionType;
import edu.miu.cs489.hsumin.personalbudgettracker.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @Test
    void createTransaction_validInput_returnTransaction() {
        // Prepare
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(100.0, LocalDate.now(), "Groceries", TransactionType.EXPENSE, 1,1L);
        TransactionResponseDTO responseDTO = new TransactionResponseDTO(1L, 100.0, LocalDate.now(), "Groceries", TransactionType.EXPENSE, null,null);

        Mockito.when(transactionService.registerTransaction(requestDTO)).thenReturn(Optional.of(responseDTO));

        // Act
        ResponseEntity<TransactionResponseDTO> responseEntity = transactionController.createTransaction(requestDTO);

        // Assert
        assert responseEntity.getStatusCode() == HttpStatus.CREATED;
        assert responseEntity.getBody()!=null;
        assert responseEntity.getBody().equals(responseDTO);
        Mockito.verify(transactionService).registerTransaction(requestDTO);
    }

    @Test
    void updateTransaction_validInput_returnTransaction() {
        // Prepare
        Long transactionId = 1L;
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(100.0, LocalDate.now(), "Groceries", TransactionType.EXPENSE, 1,1L);
        TransactionResponseDTO responseDTO = new TransactionResponseDTO(1L, 100.0, LocalDate.now(), "Groceries", TransactionType.EXPENSE, null,null);

        Mockito.when(transactionService.updateTransaction(transactionId, requestDTO)).thenReturn(Optional.of(responseDTO));

        // Act
        ResponseEntity<TransactionResponseDTO> responseEntity = transactionController.updateTransaction(transactionId, requestDTO);

        // Assert
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody()!=null;
        assert responseEntity.getBody().equals(responseDTO);
        Mockito.verify(transactionService).updateTransaction(transactionId, requestDTO);
    }

    @Test
    void deleteTransaction_validInput_returnNothing() {
        // Prepare
        Long transactionId = 1L;

        // Act
        ResponseEntity<Void> responseEntity = transactionController.deleteTransaction(transactionId);

        // Assert
        assert responseEntity.getStatusCode() == HttpStatus.NO_CONTENT;
        Mockito.verify(transactionService).deleteTransaction(transactionId);
    }

    @Test
    void findAllTransactions_noInput_returnTransaction() {
        // Prepare
        Integer accountHolderId = 1;
        List<TransactionResponseDTO> responseDTOList = List.of(
                new TransactionResponseDTO(1L, 100.0, LocalDate.now(), "Groceries", TransactionType.EXPENSE, null,null),
                new TransactionResponseDTO(1L, 100.0, LocalDate.now(), "Groceries", TransactionType.EXPENSE, null,null)
        );

        Mockito.when(transactionService.findAllTransactions(accountHolderId)).thenReturn(responseDTOList);

        // Act
        ResponseEntity<List<TransactionResponseDTO>> responseEntity = transactionController.findAllTransactions(accountHolderId);

        // Assert
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody()!=null;
        assert responseEntity.getBody().equals(responseDTOList);
        Mockito.verify(transactionService).findAllTransactions(accountHolderId);
    }

    @Test
    void searchTransactions_validInput_returnTransaction() {
        // Prepare
        Integer accountHolderId = 1;
        LocalDate transactionDate = LocalDate.now();
        Double amount = 100.0;
        String type = "EXPENSE";
        String description = "Groceries";
        Long categoryId = 1L;

        TransactionType transactionType = TransactionType.EXPENSE;
        List<TransactionResponseDTO> responseDTOList = List.of(
                new TransactionResponseDTO(
                        1L, 100.0, transactionDate, "Groceries", transactionType,
                        new AccountHolderResponseDTO(1,"Alice", Role.ACCOUNT_HOLDER,"test@gmail.com", "1234567890",null),
                        new CategoryResponseDTO(1L, "Electronics", "Category for electronic items")
                        )
        );

        Mockito.when(transactionService.searchTransactions(
                accountHolderId,
                transactionDate,
                amount,
                transactionType,
                description,
                categoryId
        )).thenReturn(responseDTOList);

        // Act
       ResponseEntity<List<TransactionResponseDTO>> responseEntity = transactionController.searchTransactions(
                accountHolderId,
                transactionDate,
                amount,
                type,
                description,
                categoryId
        );

        // Assert
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody()!=null;
        assert Objects.requireNonNull(responseEntity.getBody()).size() == 1;
        assert responseEntity.getBody().equals(responseDTOList);
        Mockito.verify(transactionService).searchTransactions(
                accountHolderId,
                transactionDate,
                amount,
                transactionType,
                description,
                categoryId
        );
    }
}
