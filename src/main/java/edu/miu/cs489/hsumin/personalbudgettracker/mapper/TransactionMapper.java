package edu.miu.cs489.hsumin.personalbudgettracker.mapper;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.TransactionRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.TransactionResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction transactionRequestDTOToTransaction(TransactionRequestDTO transactionRequestDTO);
    @Mapping(source ="transaction.accountHolder" ,target = "accountHolderResponseDTO")
    @Mapping(source ="transaction.category" ,target = "categoryResponseDTO")
    TransactionResponseDTO transactionToTransactionResponseDTO(Transaction transaction);
}
