package edu.miu.cs489.hsumin.personalbudgettracker;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.*;
import edu.miu.cs489.hsumin.personalbudgettracker.model.*;
import edu.miu.cs489.hsumin.personalbudgettracker.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication(scanBasePackages = "edu.miu.cs489.hsumin.personalbudgettracker")
@RequiredArgsConstructor
public class PersonalBudgetTrackerApplication {

    private final AccountHolderService accountHolderService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final TransactionService transactionService;

    public static void main(String[] args) {
        SpringApplication.run(PersonalBudgetTrackerApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(){
        return args -> {
            System.out.println();
            //Create User(Admin)
            UserRequestDTO userRequestDTO=new UserRequestDTO("Tim","641-233-9993","$2a$10$Maz./t.v9TTH4j.UcGBF/emsn8pXujIZAieygcPhoMloi9GTLAnn6", "tim@gmail.com", Role.ADMIN);
            System.out.println("Admin =>>>: "+userService.createUserAdmin(userRequestDTO)+"is saved");

            //Create Address
            AddressRequestDTO addressRequestDTO = new AddressRequestDTO("New York","5th Avenue","123",10001,"USA","NY");

            //Create User(AccountHolder)
            AccountHolderRequestDTO accountHolderRequestDTO=new AccountHolderRequestDTO("Jimmy","641-222-9922","$2a$10$Maz./t.v9TTH4j.UcGBF/emsn8pXujIZAieygcPhoMloi9GTLAnn6","jimmy@gmail.com", Role.ACCOUNT_HOLDER,addressRequestDTO);
            System.out.println("Account Holder1 =>>>: "+accountHolderService.createAccountHolder(accountHolderRequestDTO)+"is saved");

            //Create Category
            CategoryRequestDTO categoryRequestDTO= new CategoryRequestDTO("Transportation", "Daily Expense", 2);
            System.out.println("New Category =>>: "+categoryService.createCategory(categoryRequestDTO)+" is saved.");

            //Create Transaction
            TransactionRequestDTO transactionRequestDTO=new TransactionRequestDTO(50.0, LocalDate.now(),"Daily Transportation" , TransactionType.INCOME,2,1L);
            System.out.println("New Transaction1 =>>: "+transactionService.registerTransaction(transactionRequestDTO));
            TransactionRequestDTO transactionRequestDTO1=new TransactionRequestDTO(30.0, LocalDate.now(),"Daily Transportation" , TransactionType.EXPENSE,2,1L);
            System.out.println("New Transaction2 =>>: "+transactionService.registerTransaction(transactionRequestDTO1));

            //Create Budget


            System.out.println();
        };
    }
}
