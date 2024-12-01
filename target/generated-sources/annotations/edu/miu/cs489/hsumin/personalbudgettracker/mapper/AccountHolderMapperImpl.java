package edu.miu.cs489.hsumin.personalbudgettracker.mapper;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.AccountHolderRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.AddressRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.AccountHolderResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.AddressResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.model.AccountHolder;
import edu.miu.cs489.hsumin.personalbudgettracker.model.Address;
import edu.miu.cs489.hsumin.personalbudgettracker.model.Role;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-30T19:49:19-0600",
    comments = "version: 1.6.2, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class AccountHolderMapperImpl implements AccountHolderMapper {

    @Override
    public AccountHolder accountHolderRequestDTOToAccountHolder(AccountHolderRequestDTO accountHolderRequestDTO) {
        if ( accountHolderRequestDTO == null ) {
            return null;
        }

        AccountHolder accountHolder = new AccountHolder();

        accountHolder.setAddress( addressRequestDTOToAddress( accountHolderRequestDTO.addressRequestDTO() ) );
        accountHolder.setName( accountHolderRequestDTO.name() );
        accountHolder.setPhone( accountHolderRequestDTO.phone() );
        accountHolder.setPassword( accountHolderRequestDTO.password() );
        accountHolder.setEmail( accountHolderRequestDTO.email() );
        accountHolder.setRole( accountHolderRequestDTO.role() );

        return accountHolder;
    }

    @Override
    public AccountHolderResponseDTO accountHolderToAccountHolderResponseDTO(AccountHolder accountHolder) {
        if ( accountHolder == null ) {
            return null;
        }

        AddressResponseDTO addressResponseDTO = null;
        Integer id = null;
        String name = null;
        Role role = null;
        String email = null;
        String phone = null;

        addressResponseDTO = addressToAddressResponseDTO( accountHolder.getAddress() );
        id = accountHolder.getId();
        name = accountHolder.getName();
        role = accountHolder.getRole();
        email = accountHolder.getEmail();
        phone = accountHolder.getPhone();

        AccountHolderResponseDTO accountHolderResponseDTO = new AccountHolderResponseDTO( id, name, role, email, phone, addressResponseDTO );

        return accountHolderResponseDTO;
    }

    protected Address addressRequestDTOToAddress(AddressRequestDTO addressRequestDTO) {
        if ( addressRequestDTO == null ) {
            return null;
        }

        Address address = new Address();

        address.setCity( addressRequestDTO.city() );
        address.setStreet( addressRequestDTO.street() );
        address.setNumber( addressRequestDTO.number() );
        address.setPostalCode( addressRequestDTO.postalCode() );
        address.setCountry( addressRequestDTO.country() );
        address.setState( addressRequestDTO.state() );

        return address;
    }

    protected AddressResponseDTO addressToAddressResponseDTO(Address address) {
        if ( address == null ) {
            return null;
        }

        String city = null;
        String street = null;
        String number = null;
        Integer postalCode = null;
        String country = null;
        String state = null;

        city = address.getCity();
        street = address.getStreet();
        number = address.getNumber();
        postalCode = address.getPostalCode();
        country = address.getCountry();
        state = address.getState();

        AddressResponseDTO addressResponseDTO = new AddressResponseDTO( city, street, number, postalCode, country, state );

        return addressResponseDTO;
    }
}
