package edu.miu.cs489.hsumin.personalbudgettracker.service.impl;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.AccountHolderRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.AddressRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.AccountHolderResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.AddressResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.mapper.AccountHolderMapper;
import edu.miu.cs489.hsumin.personalbudgettracker.model.AccountHolder;
import edu.miu.cs489.hsumin.personalbudgettracker.model.Address;
import edu.miu.cs489.hsumin.personalbudgettracker.model.Role;
import edu.miu.cs489.hsumin.personalbudgettracker.repository.AccountHolderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountHolderServiceImplTest {

    @InjectMocks
    private AccountHolderServiceImpl accountHolderService;

    @Mock
    private AccountHolderRepository accountHolderRepository;

    @Mock
    private AccountHolderMapper accountHolderMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void createAccountHolder_validInput_returnAccountHolder() {
        // Prepare
        AccountHolderRequestDTO requestDTO = new AccountHolderRequestDTO("John Doe", "1234567890", "Password@123", "john@example.com", Role.ACCOUNT_HOLDER,
                new AddressRequestDTO("New York", "5th Avenue", "123", 10001, "USA", "NY"));
        AccountHolder accountHolder = new AccountHolder();
        AccountHolderResponseDTO responseDTO = new AccountHolderResponseDTO(1,"John Doe", Role.ACCOUNT_HOLDER,"test@gmail.com","1234567890",
                new AddressResponseDTO("New York", "5th Avenue", "123", 10001, "USA", "NY"));

        Mockito.when(accountHolderMapper.accountHolderRequestDTOToAccountHolder(requestDTO)).thenReturn(accountHolder);
        Mockito.when(accountHolderRepository.save(accountHolder)).thenReturn(accountHolder);
        Mockito.when(accountHolderMapper.accountHolderToAccountHolderResponseDTO(accountHolder)).thenReturn(responseDTO);

        // Act
        Optional<AccountHolderResponseDTO> result = accountHolderService.createAccountHolder(requestDTO);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(responseDTO, result.get());
        Mockito.verify(accountHolderRepository).save(accountHolder);
    }

    @Test
    void findAllAccountHolders_noInput_returnAccountHolder() {
        // Prepare
        AccountHolder accountHolder = new AccountHolder();
        List<AccountHolder> accountHolderList = List.of(accountHolder);
        AccountHolderResponseDTO responseDTO = new AccountHolderResponseDTO(1,"John Doe", Role.ACCOUNT_HOLDER, "test@gmail.com","1234567890",null);
        Mockito.when(accountHolderRepository.findAll()).thenReturn(accountHolderList);
        Mockito.when(accountHolderMapper.accountHolderToAccountHolderResponseDTO(accountHolder)).thenReturn(responseDTO);

        // Act
        List<AccountHolderResponseDTO> result = accountHolderService.findAllAccountHolders();

        // Assert
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(responseDTO, result.get(0));
        Mockito.verify(accountHolderRepository).findAll();
    }

    @Test
    void updateAccountHolder_validInput_returnAccountHolder() {
        // Prepare
        Integer id = 1;
        AccountHolderRequestDTO requestDTO = new AccountHolderRequestDTO(
                "John Updated",
                "1234567890",
                "UpdatedPassword",
                "updated@example.com",
                Role.ACCOUNT_HOLDER,
                new AddressRequestDTO("Los Angeles", "Main St", "456", 90001, "USA", "CA")
        );

        // Mock existing AccountHolder
        AccountHolder accountHolder = new AccountHolder();
        Address existingAddress = new Address();
        accountHolder.setAddress(existingAddress); // Ensure Address is not null
        AccountHolderResponseDTO responseDTO = new AccountHolderResponseDTO(1,
                "John Updated",
                Role.ACCOUNT_HOLDER,"test@gmail.com","1234567890",
                new AddressResponseDTO("Los Angeles", "Main St", "456", 90001, "USA", "CA")
        );

        Mockito.when(accountHolderRepository.findById(id)).thenReturn(Optional.of(accountHolder));
        Mockito.when(accountHolderRepository.save(accountHolder)).thenReturn(accountHolder);
        Mockito.when(accountHolderMapper.accountHolderToAccountHolderResponseDTO(accountHolder)).thenReturn(responseDTO);

        // Act
        Optional<AccountHolderResponseDTO> result = accountHolderService.updateAccountHolder(id, requestDTO);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(responseDTO, result.get());
        Mockito.verify(accountHolderRepository).findById(id);
        Mockito.verify(accountHolderRepository).save(accountHolder);
    }


    @Test
    void updateAccountHolderPartially_validInput_returnAccountHolder() {
        // Prepare
        Integer id = 1;
        AccountHolderRequestDTO requestDTO = new AccountHolderRequestDTO(null, null, "UpdatedPassword", null, null, null);
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setPassword("OldPassword");

        AccountHolderResponseDTO responseDTO = new AccountHolderResponseDTO(1,"John Doe", Role.ACCOUNT_HOLDER, "test@gmail.com","1234567890",null);
        Mockito.when(accountHolderRepository.findById(id)).thenReturn(Optional.of(accountHolder));
        Mockito.when(passwordEncoder.encode(requestDTO.password())).thenReturn("EncodedPassword");
        Mockito.when(accountHolderRepository.save(accountHolder)).thenReturn(accountHolder);
        Mockito.when(accountHolderMapper.accountHolderToAccountHolderResponseDTO(accountHolder)).thenReturn(responseDTO);

        // Act
        Optional<AccountHolderResponseDTO> result = accountHolderService.updateAccountHolderPartially(id, requestDTO);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(responseDTO, result.get());
        Mockito.verify(accountHolderRepository).findById(id);
        Mockito.verify(accountHolderRepository).save(accountHolder);
    }

    @Test
    void findByAccountHolderID_validInput_returnAccountHolder() {
        // Prepare
        Integer id = 1;
        AccountHolder accountHolder = new AccountHolder();
        AccountHolderResponseDTO responseDTO = new AccountHolderResponseDTO(1,"John Doe", Role.ACCOUNT_HOLDER, "test@gmail.com","1234567890",null);

        Mockito.when(accountHolderRepository.findById(id)).thenReturn(Optional.of(accountHolder));
        Mockito.when(accountHolderMapper.accountHolderToAccountHolderResponseDTO(accountHolder)).thenReturn(responseDTO);

        // Act
        Optional<AccountHolderResponseDTO> result = accountHolderService.findByAccountHolderID(id);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(responseDTO, result.get());
        Mockito.verify(accountHolderRepository).findById(id);
    }

    @Test
    void deleteAccountHolderById_validInput_returnNothing() {
        // Prepare
        Integer id = 1;
        Mockito.when(accountHolderRepository.findById(id)).thenReturn(Optional.of(new AccountHolder()));

        // Act
        accountHolderService.deleteAccountHolderById(id);

        // Assert
        Mockito.verify(accountHolderRepository).findById(id);
        Mockito.verify(accountHolderRepository).deleteById(id);
        Mockito.verifyNoMoreInteractions(accountHolderRepository, accountHolderMapper);
    }

    @Test
    void searchAccountHolders_validInput_returnAccountHolder() {
        // Prepare
        String name = "John Doe";

        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setName("John Doe"); // Ensure the name is set
        List<AccountHolder> accountHolders = List.of(accountHolder);
        AccountHolderResponseDTO responseDTO = new AccountHolderResponseDTO(1,"John Doe", Role.ACCOUNT_HOLDER, "test@gmail.com","1234567890",null);

        Mockito.when(accountHolderRepository.findAll()).thenReturn(accountHolders);
        Mockito.when(accountHolderMapper.accountHolderToAccountHolderResponseDTO(accountHolder)).thenReturn(responseDTO);

        // Act
        List<AccountHolderResponseDTO> result = accountHolderService.searchAccountHolders(name, null, null);

        // Assert
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(responseDTO, result.getFirst()); // Corrected to result.get(0)
        Mockito.verify(accountHolderRepository).findAll();
    }

}
