package edu.miu.cs489.hsumin.personalbudgettracker.mapper;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.TransactionRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.AccountHolderResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.AddressResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.CategoryResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.TransactionResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.model.AccountHolder;
import edu.miu.cs489.hsumin.personalbudgettracker.model.Category;
import edu.miu.cs489.hsumin.personalbudgettracker.model.Role;
import edu.miu.cs489.hsumin.personalbudgettracker.model.Transaction;
import edu.miu.cs489.hsumin.personalbudgettracker.model.TransactionType;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-30T19:49:18-0600",
    comments = "version: 1.6.2, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transaction transactionRequestDTOToTransaction(TransactionRequestDTO transactionRequestDTO) {
        if ( transactionRequestDTO == null ) {
            return null;
        }

        Transaction transaction = new Transaction();

        transaction.setAmount( transactionRequestDTO.amount() );
        transaction.setTransactionDate( transactionRequestDTO.transactionDate() );
        transaction.setDescription( transactionRequestDTO.description() );
        transaction.setType( transactionRequestDTO.type() );

        return transaction;
    }

    @Override
    public TransactionResponseDTO transactionToTransactionResponseDTO(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        AccountHolderResponseDTO accountHolderResponseDTO = null;
        CategoryResponseDTO categoryResponseDTO = null;
        Long id = null;
        Double amount = null;
        LocalDate transactionDate = null;
        String description = null;
        TransactionType type = null;

        accountHolderResponseDTO = accountHolderToAccountHolderResponseDTO( transaction.getAccountHolder() );
        categoryResponseDTO = categoryToCategoryResponseDTO( transaction.getCategory() );
        id = transaction.getId();
        amount = transaction.getAmount();
        transactionDate = transaction.getTransactionDate();
        description = transaction.getDescription();
        type = transaction.getType();

        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO( id, amount, transactionDate, description, type, accountHolderResponseDTO, categoryResponseDTO );

        return transactionResponseDTO;
    }

    protected AccountHolderResponseDTO accountHolderToAccountHolderResponseDTO(AccountHolder accountHolder) {
        if ( accountHolder == null ) {
            return null;
        }

        Integer id = null;
        String name = null;
        Role role = null;
        String email = null;
        String phone = null;

        id = accountHolder.getId();
        name = accountHolder.getName();
        role = accountHolder.getRole();
        email = accountHolder.getEmail();
        phone = accountHolder.getPhone();

        AddressResponseDTO addressResponseDTO = null;

        AccountHolderResponseDTO accountHolderResponseDTO = new AccountHolderResponseDTO( id, name, role, email, phone, addressResponseDTO );

        return accountHolderResponseDTO;
    }

    protected CategoryResponseDTO categoryToCategoryResponseDTO(Category category) {
        if ( category == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;

        id = category.getId();
        name = category.getName();
        description = category.getDescription();

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO( id, name, description );

        return categoryResponseDTO;
    }
}
