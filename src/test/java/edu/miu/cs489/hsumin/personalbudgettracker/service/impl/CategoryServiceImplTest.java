package edu.miu.cs489.hsumin.personalbudgettracker.service.impl;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.CategoryRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.CategoryResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.exception.category.UnableToDeleteCategory;
import edu.miu.cs489.hsumin.personalbudgettracker.mapper.CategoryMapper;
import edu.miu.cs489.hsumin.personalbudgettracker.model.AccountHolder;
import edu.miu.cs489.hsumin.personalbudgettracker.model.AuditData;
import edu.miu.cs489.hsumin.personalbudgettracker.model.Category;
import edu.miu.cs489.hsumin.personalbudgettracker.repository.AccountHolderRepository;
import edu.miu.cs489.hsumin.personalbudgettracker.repository.CategoryRepository;
import edu.miu.cs489.hsumin.personalbudgettracker.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private AccountHolderRepository accountHolderRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    void createCategory_validInput_shouldReturnCategoryResponseDTO() {
        // Arrange
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setId(1);
        accountHolder.setName("TestAccountHolder");

        Category category = new Category();
        category.setName("TestCategory");
        category.setDescription("TestDescription");
        category.setAccountHolder(accountHolder);

        AuditData auditData = new AuditData();
        auditData.setCreatedBy("TestAccountHolder");

        Category savedCategory = new Category();
        savedCategory.setName("TestCategory");
        savedCategory.setDescription("TestDescription");
        savedCategory.setAccountHolder(accountHolder);
        savedCategory.setAuditData(auditData);

        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO("TestCategory", "TestDescription", 1);
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(1L, "TestCategory", "TestDescription");

        // Mock behavior
        Mockito.when(accountHolderRepository.findById(1)).thenReturn(Optional.of(accountHolder));
        Mockito.when(categoryMapper.categoryRequestDTOToCategory(categoryRequestDTO)).thenReturn(category);
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(savedCategory);
        Mockito.when(categoryMapper.categoryToCategoryResponseDTO(savedCategory)).thenReturn(categoryResponseDTO);

        // Act
        Optional<CategoryResponseDTO> result = categoryService.createCategory(categoryRequestDTO);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(categoryResponseDTO.name(), result.get().name());
        Assertions.assertEquals(categoryResponseDTO.description(), result.get().description());
        Mockito.verify(accountHolderRepository).findById(1);
        Mockito.verify(categoryRepository).save(category);
    }

    @Test
    void findByCategoryID_validInput_shouldReturnCategoryResponseDTO() {
        // Arrange
        Long categoryId = 1L;

        Category category = new Category();
        category.setId(categoryId);
        category.setName("TestCategory");
        category.setDescription("TestDescription");

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(categoryId, "TestCategory", "TestDescription");

        // Mock behavior
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Mockito.when(categoryMapper.categoryToCategoryResponseDTO(category)).thenReturn(categoryResponseDTO);

        // Act
        Optional<CategoryResponseDTO> result = categoryService.findByCategoryID(categoryId);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(categoryResponseDTO.id(), result.get().id());
        Assertions.assertEquals(categoryResponseDTO.name(), result.get().name());
        Assertions.assertEquals(categoryResponseDTO.description(), result.get().description());
        Mockito.verify(categoryRepository).findById(categoryId);
        Mockito.verify(categoryMapper).categoryToCategoryResponseDTO(category);
    }


    @Test
    void updateCategory_validInput_shouldReturnCategoryResponseDTO() {
        // Arrange
        Long categoryId = 1L;

        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setId(1);
        accountHolder.setName("TestAccountHolder");

        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setName("OldCategoryName");
        existingCategory.setDescription("OldDescription");
        existingCategory.setAccountHolder(accountHolder);

        Category updatedCategory = new Category();
        updatedCategory.setId(categoryId);
        updatedCategory.setName("NewCategoryName");
        updatedCategory.setDescription("NewDescription");
        updatedCategory.setAccountHolder(accountHolder);

        AuditData auditData = new AuditData();
        auditData.setUpdatedBy("TestAccountHolder");

        updatedCategory.setAuditData(auditData);

        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO("NewCategoryName", "NewDescription", null);
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(categoryId, "NewCategoryName", "NewDescription");

        // Mock behavior
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(updatedCategory);
        Mockito.when(categoryMapper.categoryToCategoryResponseDTO(updatedCategory)).thenReturn(categoryResponseDTO);

        // Act
        Optional<CategoryResponseDTO> result = categoryService.updateCategory(categoryId, categoryRequestDTO);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(categoryResponseDTO.id(), result.get().id());
        Assertions.assertEquals(categoryResponseDTO.name(), result.get().name());
        Assertions.assertEquals(categoryResponseDTO.description(), result.get().description());
        Mockito.verify(categoryRepository).findById(categoryId);
        Mockito.verify(categoryRepository).save(Mockito.any(Category.class));
        Mockito.verify(categoryMapper).categoryToCategoryResponseDTO(updatedCategory);
    }


    @Test
    void findAllCategory_validInput_shouldReturnCategoryResponseDTO() {
        // Arrange
        Integer accountHolderId = 1;

        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setId(accountHolderId);
        accountHolder.setName("TestAccountHolder");

        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category1");
        category1.setDescription("Description1");
        category1.setAccountHolder(accountHolder);

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Category2");
        category2.setDescription("Description2");
        category2.setAccountHolder(accountHolder);

        List<Category> categoryList = List.of(category1, category2);

        CategoryResponseDTO responseDTO1 = new CategoryResponseDTO(1L, "Category1", "Description1");
        CategoryResponseDTO responseDTO2 = new CategoryResponseDTO(2L, "Category2", "Description2");

        List<CategoryResponseDTO> expectedResponseDTOList = List.of(responseDTO1, responseDTO2);

        // Mock behavior
        Mockito.when(categoryRepository.findByAccountHolderId(accountHolderId)).thenReturn(categoryList);
        Mockito.when(categoryMapper.categoryToCategoryResponseDTO(category1)).thenReturn(responseDTO1);
        Mockito.when(categoryMapper.categoryToCategoryResponseDTO(category2)).thenReturn(responseDTO2);

        // Act
        List<CategoryResponseDTO> result = categoryService.findAllCategory(accountHolderId);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedResponseDTOList.size(), result.size());
        Assertions.assertEquals(expectedResponseDTOList.get(0).name(), result.get(0).name());
        Assertions.assertEquals(expectedResponseDTOList.get(1).name(), result.get(1).name());
        Mockito.verify(categoryRepository).findByAccountHolderId(accountHolderId);
        Mockito.verify(categoryMapper).categoryToCategoryResponseDTO(category1);
        Mockito.verify(categoryMapper).categoryToCategoryResponseDTO(category2);
    }


    @Test
    void searchCategory_validInput_shouldReturnCategoryResponseDTO() {
        // Arrange
        Integer accountHolderId = 1;
        String name = "TestCategory";
        String description = "TestDescription";

        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setId(accountHolderId);
        accountHolder.setName("TestAccountHolder");

        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("TestCategory");
        category1.setDescription("TestDescription");
        category1.setAccountHolder(accountHolder);

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("OtherCategory");
        category2.setDescription("OtherDescription");
        category2.setAccountHolder(accountHolder);

        List<Category> categories = List.of(category1, category2);

        CategoryResponseDTO responseDTO1 = new CategoryResponseDTO(1L, "TestCategory", "TestDescription");

        // Mock behavior
        Mockito.when(categoryRepository.findByAccountHolderId(accountHolderId)).thenReturn(categories);
        Mockito.when(categoryMapper.categoryToCategoryResponseDTO(category1)).thenReturn(responseDTO1);

        // Act
        List<CategoryResponseDTO> result = categoryService.searchCategory(accountHolderId, name, description);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(responseDTO1.name(), result.get(0).name());
        Assertions.assertEquals(responseDTO1.description(), result.get(0).description());
        Mockito.verify(categoryRepository).findByAccountHolderId(accountHolderId);
        Mockito.verify(categoryMapper).categoryToCategoryResponseDTO(category1);
    }


    @Test
    void deleteCategory_categoryHasTransactions_shouldThrowUnableToDeleteCategory() {
        // Arrange
        Integer accountHolderId = 1;
        Long categoryId = 1L;

        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setId(accountHolderId);
        accountHolder.setName("TestAccountHolder");

        Category category = new Category();
        category.setId(categoryId);
        category.setName("TestCategory");
        category.setDescription("TestDescription");
        category.setAccountHolder(accountHolder);

        // Mock behavior
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Mockito.when(transactionRepository.existsByCategory(category)).thenReturn(true);

        // Act & Assert
        Assertions.assertThrows(UnableToDeleteCategory.class,
                () -> categoryService.deleteCategory(accountHolderId, categoryId));
        Mockito.verify(categoryRepository).findById(categoryId);
        Mockito.verify(transactionRepository).existsByCategory(category);
    }

}