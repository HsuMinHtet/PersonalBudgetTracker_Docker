package edu.miu.cs489.hsumin.personalbudgettracker.mapper;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.AccountHolderRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.AccountHolderResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.model.AccountHolder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface AccountHolderMapper {
    @Mapping(source ="accountHolderRequestDTO.addressRequestDTO" ,target = "address")
    AccountHolder accountHolderRequestDTOToAccountHolder(AccountHolderRequestDTO accountHolderRequestDTO);
    @Mapping(source = "accountHolder.address", target = "addressResponseDTO")
    AccountHolderResponseDTO accountHolderToAccountHolderResponseDTO(AccountHolder accountHolder);
}
