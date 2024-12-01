package edu.miu.cs489.hsumin.personalbudgettracker.service.impl;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.AccountHolderRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.AccountHolderResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.exception.accountHolder.UnableToDeleteAccountHolder;
import edu.miu.cs489.hsumin.personalbudgettracker.exception.accountHolder.UserNotFoundException;
import edu.miu.cs489.hsumin.personalbudgettracker.exception.category.UnableToDeleteCategory;
import edu.miu.cs489.hsumin.personalbudgettracker.mapper.AccountHolderMapper;
import edu.miu.cs489.hsumin.personalbudgettracker.model.AccountHolder;
import edu.miu.cs489.hsumin.personalbudgettracker.model.Address;
import edu.miu.cs489.hsumin.personalbudgettracker.model.AuditData;
import edu.miu.cs489.hsumin.personalbudgettracker.repository.AccountHolderRepository;
import edu.miu.cs489.hsumin.personalbudgettracker.service.AccountHolderService;
import edu.miu.cs489.hsumin.personalbudgettracker.util.AuditDataCreate;
import edu.miu.cs489.hsumin.personalbudgettracker.util.AuditDataUpdate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountHolderServiceImpl implements AccountHolderService {

    private final AccountHolderMapper accountHolderMapper;
    private final AccountHolderRepository accountHolderRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<AccountHolderResponseDTO> createAccountHolder(AccountHolderRequestDTO accountHolderRequestDTO) {
        AccountHolder accountHolder=accountHolderMapper.accountHolderRequestDTOToAccountHolder(accountHolderRequestDTO);
        AuditData auditData = AuditDataCreate.populateAuditData(accountHolderRequestDTO.name());
        accountHolder.setAuditData(auditData);
       return Optional.of(accountHolderMapper.accountHolderToAccountHolderResponseDTO(accountHolderRepository.save(accountHolder)));
    }

    @Override
    public List<AccountHolderResponseDTO> findAllAccountHolders() {
        List<AccountHolder> accountHolders= accountHolderRepository.findAll();
        List<AccountHolderResponseDTO> accountHolderResponseDTOS= new ArrayList<>();
        for(AccountHolder accountHolder:accountHolders){
            accountHolderResponseDTOS.add(accountHolderMapper.accountHolderToAccountHolderResponseDTO(accountHolder));
        }
        return accountHolderResponseDTOS;
    }

    @Override
    public Optional<AccountHolderResponseDTO> updateAccountHolder(Integer id, AccountHolderRequestDTO accountHolderRequestDTO) {
        Optional<AccountHolder> foundAccountHolder=accountHolderRepository.findById(id);
        if(foundAccountHolder.isPresent()){
            AccountHolder accountHolder= foundAccountHolder.get();
            accountHolder.setName(accountHolderRequestDTO.name());
            //accountHolder.setPassword(accountHolderRequestDTO.password());
            accountHolder.setPhone(accountHolderRequestDTO.phone());
           // accountHolder.setEmail(accountHolderRequestDTO.email());
            Address address = getAddress(accountHolderRequestDTO, accountHolder);
            accountHolder.setAddress(address);
            AuditData auditData = AuditDataUpdate.populateAuditData(accountHolderRequestDTO.name());
            accountHolder.setAuditData(auditData);
            return Optional.of(accountHolderMapper.accountHolderToAccountHolderResponseDTO(accountHolderRepository.save(accountHolder)));
        }
        throw new UserNotFoundException("Account is not found.");
    }

    private static Address getAddress(AccountHolderRequestDTO accountHolderRequestDTO, AccountHolder accountHolder) {
        Address address= accountHolder.getAddress();
        if (address == null) {
            address = new Address(); // Create a new address if null
        }
        address.setCity(accountHolderRequestDTO.addressRequestDTO().city());
        address.setState(accountHolderRequestDTO.addressRequestDTO().state());
        address.setNumber(accountHolderRequestDTO.addressRequestDTO().number());
        address.setStreet(accountHolderRequestDTO.addressRequestDTO().street());
        address.setPostalCode(accountHolderRequestDTO.addressRequestDTO().postalCode());
        address.setCountry(accountHolderRequestDTO.addressRequestDTO().country());
        return address;
    }

    @Override
    public Optional<AccountHolderResponseDTO> updateAccountHolderPartially(Integer id, AccountHolderRequestDTO accountHolderRequestDTO) {
        Optional<AccountHolder> foundAccountHolder=accountHolderRepository.findById(id);
        if(foundAccountHolder.isPresent()){
            AccountHolder accountHolder= foundAccountHolder.get();

            if(accountHolderRequestDTO.password()!=null){
                accountHolder.setPassword(passwordEncoder.encode(accountHolderRequestDTO.password()));
            }
            return Optional.of(accountHolderMapper.accountHolderToAccountHolderResponseDTO(accountHolderRepository.save(accountHolder)));
        }
        throw new UserNotFoundException("Account is not found.");
    }


    public Optional<AccountHolderResponseDTO> findByAccountHolderID(Integer id) {
        return Optional.of(accountHolderMapper.accountHolderToAccountHolderResponseDTO( accountHolderRepository.findById(id).get()));
    }

    @Override
    @Transactional
    public void deleteAccountHolderById(Integer id) {
        Optional<AccountHolder> findAccountHolder = accountHolderRepository.findById(id);
        if(findAccountHolder.isPresent()){
            boolean hasNoTransaction =findAccountHolder.get().getTransactions().isEmpty();

            if(hasNoTransaction){
                accountHolderRepository.deleteById(id);
                return;
            }
            throw new UnableToDeleteAccountHolder("This accountHolder has association with other. Not allow to delete!");
        }
    }

    @Override
    public List<AccountHolderResponseDTO> searchAccountHolders(String name, String city, String country) {
        List<AccountHolder> filteredAccountHolders = accountHolderRepository.findAll().stream()
                .filter(accountHolder -> (name == null || accountHolder.getName().equalsIgnoreCase(name)))
                .filter(accountHolder -> (city == null || accountHolder.getAddress().getCity().equalsIgnoreCase(city)))
                .filter(accountHolder -> (country == null || accountHolder.getAddress().getCountry().equalsIgnoreCase(country)))
                .toList();

        return filteredAccountHolders.stream()
                .map(accountHolderMapper::accountHolderToAccountHolderResponseDTO)
                .collect(Collectors.toList());
    }
}
