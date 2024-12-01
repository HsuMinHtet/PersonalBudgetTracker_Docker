package edu.miu.cs489.hsumin.personalbudgettracker.service.impl;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.TransactionRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.TransactionResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.mapper.TransactionMapper;
import edu.miu.cs489.hsumin.personalbudgettracker.model.AccountHolder;
import edu.miu.cs489.hsumin.personalbudgettracker.model.Category;
import edu.miu.cs489.hsumin.personalbudgettracker.model.Transaction;
import edu.miu.cs489.hsumin.personalbudgettracker.model.TransactionType;
import edu.miu.cs489.hsumin.personalbudgettracker.repository.AccountHolderRepository;
import edu.miu.cs489.hsumin.personalbudgettracker.repository.CategoryRepository;
import edu.miu.cs489.hsumin.personalbudgettracker.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountHolderRepository accountHolderRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @Test
    void registerTransaction_validInput_returnTransaction() {
        // Prepare
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(
                100.00, LocalDate.now(), "this month salary", TransactionType.INCOME, 1,1L
        );
        AccountHolder accountHolder = new AccountHolder();
        Category category = new Category();
        Transaction transaction = new Transaction();
        TransactionResponseDTO responseDTO = new TransactionResponseDTO(1L, 100.00, LocalDate.now(), "this month salary", TransactionType.INCOME, null,null);

        Mockito.when(accountHolderRepository.findById(transactionRequestDTO.accountHolderId())).thenReturn(Optional.of(accountHolder));
        Mockito.when(categoryRepository.findById(transactionRequestDTO.categoryId())).thenReturn(Optional.of(category));
        Mockito.when(transactionMapper.transactionRequestDTOToTransaction(transactionRequestDTO)).thenReturn(transaction);
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
        Mockito.when(transactionMapper.transactionToTransactionResponseDTO(transaction)).thenReturn(responseDTO);

        // Act
        Optional<TransactionResponseDTO> result = transactionService.registerTransaction(transactionRequestDTO);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(responseDTO, result.get());
        Mockito.verify(accountHolderRepository).findById(transactionRequestDTO.accountHolderId());
        Mockito.verify(categoryRepository).findById(transactionRequestDTO.categoryId());
        Mockito.verify(transactionRepository).save(transaction);
    }

    @Test
    void updateTransaction_validInput_returnTransaction() {
        // Prepare
        Long transactionId = 1L;
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(
                100.00, LocalDate.now(), "this month salary", TransactionType.INCOME, 1, 1L
        );
        Transaction transaction = new Transaction();

        // Mock the AccountHolder and set it in the transaction
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setName("John Doe");  // Assuming the AccountHolder has a name
        transaction.setAccountHolder(accountHolder);

        Category category = new Category();
        TransactionResponseDTO responseDTO = new TransactionResponseDTO(1L, 100.00, LocalDate.now(), "this month salary", TransactionType.INCOME, null, null);

        // Mock repository calls
        Mockito.when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        Mockito.when(categoryRepository.findById(transactionRequestDTO.categoryId())).thenReturn(Optional.of(category));
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
        Mockito.when(transactionMapper.transactionToTransactionResponseDTO(transaction)).thenReturn(responseDTO);

        // Act
        Optional<TransactionResponseDTO> result = transactionService.updateTransaction(transactionId, transactionRequestDTO);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(responseDTO, result.get());
        Mockito.verify(transactionRepository).findById(transactionId);
        Mockito.verify(categoryRepository).findById(transactionRequestDTO.categoryId());
        Mockito.verify(transactionRepository).save(transaction);
    }

    @Test
    void deleteTransaction_validInput_returnNothing() {
        // Prepare
        Long transactionId = 1L;
        Transaction transaction = new Transaction();

        Mockito.when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));

        // Act
        transactionService.deleteTransaction(transactionId);

        // Assert
        Mockito.verify(transactionRepository).findById(transactionId);
        Mockito.verify(transactionRepository).deleteById(transactionId);
    }

    @Test
    void findByTransactionID_validInput_returnTransaction() {
        // Prepare
        Long transactionId = 1L;
        Transaction transaction = new Transaction();
        TransactionResponseDTO responseDTO = new TransactionResponseDTO(1L, 100.00, LocalDate.now(), "this month salary", TransactionType.INCOME, null,null);

        Mockito.when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        Mockito.when(transactionMapper.transactionToTransactionResponseDTO(transaction)).thenReturn(responseDTO);

        // Act
        Optional<TransactionResponseDTO> result = transactionService.findByTransactionID(transactionId);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(responseDTO, result.get());
        Mockito.verify(transactionRepository).findById(transactionId);
    }

    @Test
    void findAllTransactions_noInput_returnTransaction() {
        // Prepare
        Integer accountHolderId = 1;
        List<Transaction> transactions = List.of(new Transaction());
        TransactionResponseDTO responseDTO = new TransactionResponseDTO(1L, 100.00, LocalDate.now(), "this month salary", TransactionType.INCOME, null,null);

        Mockito.when(transactionRepository.findALLByAccountHolder_Id(accountHolderId)).thenReturn(transactions);
        Mockito.when(transactionMapper.transactionToTransactionResponseDTO(Mockito.any(Transaction.class))).thenReturn(responseDTO);

        // Act
        List<TransactionResponseDTO> result = transactionService.findAllTransactions(accountHolderId);

        // Assert
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(responseDTO, result.get(0));
        Mockito.verify(transactionRepository).findALLByAccountHolder_Id(accountHolderId);
    }


    @Test
    void searchTransactions_noInput_returnTransaction() {
        // Arrange
        Integer accountHolderId = 1;

        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setId(accountHolderId);
        accountHolder.setName("TestAccountHolder");

        Category category = new Category();
        category.setId(1L);
        category.setName("TestCategory");

        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setTransactionDate(LocalDate.of(2023, 11, 1));
        transaction1.setAmount(100.0);
        transaction1.setType(TransactionType.INCOME);
        transaction1.setDescription("TestDescription1");
        transaction1.setCategory(category);
        transaction1.setAccountHolder(accountHolder);

        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setTransactionDate(LocalDate.of(2023, 11, 2));
        transaction2.setAmount(200.0);
        transaction2.setType(TransactionType.EXPENSE);
        transaction2.setDescription("TestDescription2");
        transaction2.setCategory(category);
        transaction2.setAccountHolder(accountHolder);

        List<Transaction> transactions = List.of(transaction1, transaction2);

        TransactionResponseDTO responseDTO1 = new TransactionResponseDTO(1L, 100.00, LocalDate.of(2023, 11, 1), "TestDescription1", TransactionType.INCOME, null,null);
        TransactionResponseDTO responseDTO2 = new TransactionResponseDTO(2L, 200.00, LocalDate.of(2023, 11, 2), "TestDescription2", TransactionType.EXPENSE, null,null);

        // Mock behavior
        Mockito.when(transactionRepository.findALLByAccountHolder_Id(accountHolderId)).thenReturn(transactions);
        Mockito.when(transactionMapper.transactionToTransactionResponseDTO(transaction1)).thenReturn(responseDTO1);
        Mockito.when(transactionMapper.transactionToTransactionResponseDTO(transaction2)).thenReturn(responseDTO2);

        // Act
        List<TransactionResponseDTO> result = transactionService.searchTransactions(accountHolderId, null, null, null, null, null);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(responseDTO1.description(), result.get(0).description());
        Assertions.assertEquals(responseDTO2.description(), result.get(1).description());
        Mockito.verify(transactionRepository).findALLByAccountHolder_Id(accountHolderId);
        Mockito.verify(transactionMapper).transactionToTransactionResponseDTO(transaction1);
        Mockito.verify(transactionMapper).transactionToTransactionResponseDTO(transaction2);
    }

}
