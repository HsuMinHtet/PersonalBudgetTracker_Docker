package edu.miu.cs489.hsumin.personalbudgettracker.service.impl;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.CategoryRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.CategoryResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.exception.accountHolder.UserNotFoundException;
import edu.miu.cs489.hsumin.personalbudgettracker.exception.category.CategoryNotFoundException;
import edu.miu.cs489.hsumin.personalbudgettracker.exception.category.UnableToDeleteCategory;
import edu.miu.cs489.hsumin.personalbudgettracker.mapper.CategoryMapper;
import edu.miu.cs489.hsumin.personalbudgettracker.model.AccountHolder;
import edu.miu.cs489.hsumin.personalbudgettracker.model.AuditData;
import edu.miu.cs489.hsumin.personalbudgettracker.model.Category;
import edu.miu.cs489.hsumin.personalbudgettracker.repository.AccountHolderRepository;
import edu.miu.cs489.hsumin.personalbudgettracker.repository.CategoryRepository;
import edu.miu.cs489.hsumin.personalbudgettracker.repository.TransactionRepository;
import edu.miu.cs489.hsumin.personalbudgettracker.service.CategoryService;
import edu.miu.cs489.hsumin.personalbudgettracker.util.AuditDataCreate;
import edu.miu.cs489.hsumin.personalbudgettracker.util.AuditDataUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final AccountHolderRepository accountHolderRepository;
    private final TransactionRepository transactionRepository;


    @Override
    public Optional<CategoryResponseDTO> createCategory(CategoryRequestDTO categoryRequestDTO) {
        Optional<AccountHolder> accountHolder=  accountHolderRepository.findById(categoryRequestDTO.accountHolder_id());
        if(accountHolder.isPresent()){
            Category saveCategory=categoryMapper.categoryRequestDTOToCategory(categoryRequestDTO);
            saveCategory.setAccountHolder(accountHolder.get());
            AuditData auditData = AuditDataCreate.populateAuditData(accountHolder.get().getName());
            saveCategory.setAuditData(auditData);
            Category savedCategory=categoryRepository.save(saveCategory);
            return Optional.of(categoryMapper.categoryToCategoryResponseDTO(savedCategory));
        }
        throw new UserNotFoundException("Category is not found.");
    }

    @Override
    public Optional<CategoryResponseDTO> findByCategoryID(Long id) {
        return Optional.of(categoryMapper.categoryToCategoryResponseDTO(categoryRepository.findById(id).get()));
    }

    @Override
    public Optional<CategoryResponseDTO> updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        Optional<Category> foundCategory=categoryRepository.findById(id);
        if(foundCategory.isPresent()){
            Category category= foundCategory.get();
            if(categoryRequestDTO.name()!=null){
                category.setName(categoryRequestDTO.name());
            }
            if(categoryRequestDTO.description()!=null){
                category.setDescription(categoryRequestDTO.description());
            }
            AuditData auditData = AuditDataUpdate.populateAuditData(foundCategory.get().getAccountHolder().getName());
            category.setAuditData(auditData);
            return Optional.of(categoryMapper.categoryToCategoryResponseDTO(categoryRepository.save(category)));
        }
        throw new CategoryNotFoundException("Category is not found.");
    }

    @Override
    public List<CategoryResponseDTO> findAllCategory(Integer accountHolder_id) {
        List<Category> categories= categoryRepository.findByAccountHolderId(accountHolder_id);
        List<CategoryResponseDTO> categoryResponseDTOS= new ArrayList<>();
        for(Category category: categories){
            categoryResponseDTOS.add(categoryMapper.categoryToCategoryResponseDTO(category));
        }
        return categoryResponseDTOS;
    }

    @Override
    public List<CategoryResponseDTO> searchCategory(Integer accountHolder_id, String name, String description) {
        List<Category> filteredAccountHolders = categoryRepository.findByAccountHolderId(accountHolder_id).stream()
                .filter(category -> (name == null || category.getName().equalsIgnoreCase(name)))
                .filter(category -> (description == null || category.getDescription().equalsIgnoreCase(description)))
                .toList();

        return filteredAccountHolders.stream()
                .map(categoryMapper::categoryToCategoryResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Integer accountHolder_id, Long id) {
        // Check if the category is associated with any transactions or budgets
        Optional<Category> category=categoryRepository.findById(id);
        if (category.isPresent() && Objects.equals(accountHolder_id, category.get().getAccountHolder().getId())) {
            boolean hasTransactions = transactionRepository.existsByCategory(category.get());
            //boolean hasBudgets = budgetRepository.existsByCategory(category);

            // If no associated,
            if (!hasTransactions) {
                categoryRepository.deleteById(category.get().getId());
                return;
            }
        }
        throw new UnableToDeleteCategory("This category has association with other.Not allow to delete!");
    }

}
