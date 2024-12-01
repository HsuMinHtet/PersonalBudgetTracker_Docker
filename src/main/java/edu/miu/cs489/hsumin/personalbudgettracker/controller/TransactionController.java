package edu.miu.cs489.hsumin.personalbudgettracker.controller;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.TransactionRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.TransactionResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.model.TransactionType;
import edu.miu.cs489.hsumin.personalbudgettracker.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TransactionController {

    private final TransactionService transactionService;

    //AccountHolder (Create Transaction)
    @PreAuthorize("hasRole('ACCOUNT_HOLDER')")
    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@Valid
                                                                    @RequestBody TransactionRequestDTO transactionRequestDTO
    ){
        Optional<TransactionResponseDTO> transactionResponseDTO= transactionService.registerTransaction(transactionRequestDTO);
        return transactionResponseDTO.map(responseDTO -> ResponseEntity.status(HttpStatus.CREATED).body(responseDTO)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }
    //AccountHolder (Update Transaction)
    @PreAuthorize("hasRole('ACCOUNT_HOLDER')")
    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> updateTransaction(
            @PathVariable Long id,
            @RequestBody TransactionRequestDTO transactionRequestDTO
    ){
        Optional<TransactionResponseDTO> transactionResponseDTO= transactionService.updateTransaction(id, transactionRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(transactionResponseDTO.get());
    }
    //AccountHolder (Delete Transaction)
    @PreAuthorize("hasRole('ACCOUNT_HOLDER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction( @PathVariable Long id){
        transactionService.deleteTransaction(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //AccountHolder (Find transactionById)
    @PreAuthorize("hasRole('ACCOUNT_HOLDER')")
    @GetMapping("/{id}")
    private ResponseEntity<TransactionResponseDTO> findTransactionsById( @PathVariable Long id){
        Optional<TransactionResponseDTO> transactionResponseDTO= transactionService.findByTransactionID(id);
        if(transactionResponseDTO.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(transactionResponseDTO.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    //AccountHolder (Find All Transaction)
    @PreAuthorize("hasRole('ACCOUNT_HOLDER')")
    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> findAllTransactions(@RequestHeader Integer accountHolder_id){
        List<TransactionResponseDTO> transactionResponseDTO= transactionService.findAllTransactions(accountHolder_id);
        return ResponseEntity.status(HttpStatus.OK).body(transactionResponseDTO);
    }
    //AccountHolder(Multiple Criteria)
    @PreAuthorize("hasRole('ACCOUNT_HOLDER')")
    @GetMapping("/criteria")
    public ResponseEntity<List<TransactionResponseDTO>> searchTransactions(
            @RequestHeader Integer accountHolder_id,
            @RequestParam(required = false) LocalDate transactionDate,
            @RequestParam(required = false) Double amount,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long categoryId
    ) {
        // Ensure type is converted to the appropriate enum if needed
        TransactionType transactionType = type != null ? TransactionType.valueOf(type.toUpperCase()) : null;
        List<TransactionResponseDTO> transactionResponseDTO= transactionService.searchTransactions(
                accountHolder_id,
                transactionDate,
                amount,
                transactionType,
                description,
                categoryId
        );
        return ResponseEntity.status(HttpStatus.OK).body(transactionResponseDTO);
    }

}
