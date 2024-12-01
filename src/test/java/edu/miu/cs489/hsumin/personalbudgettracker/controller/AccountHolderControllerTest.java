package edu.miu.cs489.hsumin.personalbudgettracker.controller;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.AccountHolderRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.AddressRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.AccountHolderResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.AddressResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.model.AccountHolder;
import edu.miu.cs489.hsumin.personalbudgettracker.model.Role;
import edu.miu.cs489.hsumin.personalbudgettracker.service.AccountHolderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountHolderControllerTest {
    @InjectMocks
    private AccountHolderController accountHolderController;
    @Mock
    private AccountHolderService accountHolderService;

    @Test
    void updatePartially_validInput_returnAccountHolder() {
        // prepare
        Integer id = 1;
        AddressRequestDTO addressRequestDTO= new AddressRequestDTO(
                "New York", "5th Avenue", "123", 1001, "USA", "NY");
        AccountHolderRequestDTO accountHolderRequestDTO = new AccountHolderRequestDTO(
                "hsu","(123)-456-7890", "Password@123","hsu@gmail.com", Role.ACCOUNT_HOLDER,addressRequestDTO);
        AddressResponseDTO addressResponseDTO= new AddressResponseDTO(
                "New York", "5th Avenue", "123", 1001, "USA", "NY");
        AccountHolderResponseDTO accountHolderResponseDTO= new AccountHolderResponseDTO(1,"hsu",Role.ACCOUNT_HOLDER,"test@gmail.com","1234567890",addressResponseDTO);

        //mock
        Mockito.when(accountHolderService.updateAccountHolderPartially(id, accountHolderRequestDTO)).thenReturn(Optional.of(accountHolderResponseDTO));
        //act
        ResponseEntity<AccountHolderResponseDTO> responseEntity = accountHolderController.updatePartially(id, accountHolderRequestDTO);

        // Assert
        assert responseEntity.getStatusCode()== HttpStatus.OK;
        assert responseEntity.getBody()==accountHolderResponseDTO;
    }

    @Test
    void update_validInput_returnAccountHolder() {
        // prepare
        Integer id = 1;
        AddressRequestDTO addressRequestDTO = new AddressRequestDTO("New York", "5th Avenue", "123", 1001, "USA", "NY");
        AccountHolderRequestDTO accountHolderRequestDTO = new AccountHolderRequestDTO("hsu", "(123)-456-7890", "Password@123", "hsu@gmail.com", Role.ACCOUNT_HOLDER, addressRequestDTO);
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO("New York", "5th Avenue", "123", 1001, "USA", "NY");
        AccountHolderResponseDTO accountHolderResponseDTO = new AccountHolderResponseDTO(1,"hsu", Role.ACCOUNT_HOLDER, "test@gmail.com","1234567890",addressResponseDTO);

        // mock
        Mockito.when(accountHolderService.updateAccountHolder(id, accountHolderRequestDTO)).thenReturn(Optional.of(accountHolderResponseDTO));

        // Act
        ResponseEntity<AccountHolderResponseDTO> responseEntity = accountHolderController.update(id, accountHolderRequestDTO);

        // Assert
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody() == accountHolderResponseDTO;
    }

    @Test
    void findAccountHolderByID_validInput_returnAccountHolder() {
        // prepare
        Integer id = 1;
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO("New York", "5th Avenue", "123", 1001, "USA", "NY");
        AccountHolderResponseDTO accountHolderResponseDTO = new AccountHolderResponseDTO(1,"hsu", Role.ACCOUNT_HOLDER,"test@gmail.com","1234567890", addressResponseDTO);

        // mock
        Mockito.when(accountHolderService.findByAccountHolderID(id)).thenReturn(Optional.of(accountHolderResponseDTO));

        // Act
        ResponseEntity<AccountHolderResponseDTO> responseEntity = accountHolderController.findAccountHolderByID(id);

        // Assert
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody() == accountHolderResponseDTO;
    }

    @Test
    void deleteAccountHolder_validInput_returnNothing() {
        // prepare
        Integer id = 1;

        // mock
        Mockito.doNothing().when(accountHolderService).deleteAccountHolderById(id);

        // Act
        ResponseEntity<Void> responseEntity = accountHolderController.deleteAccountHolder(id);

        // Assert
        assert responseEntity.getStatusCode() == HttpStatus.NO_CONTENT;
        Mockito.verify(accountHolderService).deleteAccountHolderById(id);
    }

    @Test
    void findAllAccountHolder_noInput_returnAccountHolder() {
        // prepare
        AddressResponseDTO addressResponseDTO1 = new AddressResponseDTO("New York", "5th Avenue", "123", 1001, "USA", "NY");
        AccountHolderResponseDTO accountHolderResponseDTO1 = new AccountHolderResponseDTO(1,"Alice", Role.ACCOUNT_HOLDER, "test@gmail.com","1234567890",addressResponseDTO1);
        AddressResponseDTO addressResponseDTO2 = new AddressResponseDTO("Los Angeles", "Main Street", "456", 2002, "USA", "CA");
        AccountHolderResponseDTO accountHolderResponseDTO2 = new AccountHolderResponseDTO(2,"Bob", Role.ACCOUNT_HOLDER,"test@gmail.com", "1234567890",addressResponseDTO2);

        List<AccountHolderResponseDTO> accountHolders = List.of(accountHolderResponseDTO1, accountHolderResponseDTO2);

        // mock
        Mockito.when(accountHolderService.findAllAccountHolders()).thenReturn(accountHolders);

        // Act
        ResponseEntity<List<AccountHolderResponseDTO>> responseEntity = accountHolderController.findAllAccountHolder();

        // Assert
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody().size() == 2;
        assert responseEntity.getBody().containsAll(accountHolders);
    }

    @Test
    void searchAccountHolders_validInput_returnAccountHolder() {
        // prepare
        String query = "Alice";
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO("New York", "5th Avenue", "123", 1001, "USA", "NY");
        AccountHolderResponseDTO accountHolderResponseDTO = new AccountHolderResponseDTO(1,"Alice", Role.ACCOUNT_HOLDER,"test@gmail.com", "1234567890",addressResponseDTO);

        List<AccountHolderResponseDTO> searchResults = List.of(accountHolderResponseDTO);

        // mock
        Mockito.when(accountHolderService.searchAccountHolders(query,null,null)).thenReturn(searchResults);

        // Act
        ResponseEntity<List<AccountHolderResponseDTO>> responseEntity = accountHolderController.searchAccountHolders(query,null,null);

        // Assert
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody().size() == 1;
        assert responseEntity.getBody().get(0).name().equals("Alice");
    }

}