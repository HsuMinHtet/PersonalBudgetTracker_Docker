package edu.miu.cs489.hsumin.personalbudgettracker.controller;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.CategoryRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.CategoryResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @Test
    void createNewCategory_validInput_returnCategory() {
        // Prepare
        CategoryRequestDTO requestDTO = new CategoryRequestDTO("Electronics", "Category for electronic items",1);
        CategoryResponseDTO responseDTO = new CategoryResponseDTO(1L, "Electronics", "Category for electronic items");

        Mockito.when(categoryService.createCategory(requestDTO)).thenReturn(Optional.of(responseDTO));

        // Act
        ResponseEntity<CategoryResponseDTO> responseEntity = categoryController.createNewCategory(requestDTO);

        // Assert
        assert responseEntity.getStatusCode() == HttpStatus.CREATED;
        assert responseEntity.getBody()!=null;
        assert responseEntity.getBody().equals(responseDTO);
        Mockito.verify(categoryService).createCategory(requestDTO);
    }

    @Test
    void updateCategory_validInput_returnCategory() {
        // Prepare
        Long categoryId = 1L;
        CategoryRequestDTO requestDTO = new CategoryRequestDTO("Updated Electronics", "Updated description",1);
        CategoryResponseDTO responseDTO = new CategoryResponseDTO(categoryId, "Updated Electronics", "Updated description");

        Mockito.when(categoryService.updateCategory(categoryId, requestDTO)).thenReturn(Optional.of(responseDTO));

        // Act
        ResponseEntity<CategoryResponseDTO> responseEntity = categoryController.updateCategory(categoryId, requestDTO);

        // Assert
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody()!=null;
        assert responseEntity.getBody().equals(responseDTO);
        Mockito.verify(categoryService).updateCategory(categoryId, requestDTO);
    }

    @Test
    void deleteCategory_validInput_returnNothing() {
        // Prepare
        Integer accountHolderId = 1;
        Long categoryId = 1L;

        // Act
        ResponseEntity<Void> responseEntity = categoryController.deleteCategory(accountHolderId, categoryId);

        // Assert
        assert responseEntity.getStatusCode() == HttpStatus.NO_CONTENT;
        Mockito.verify(categoryService).deleteCategory(accountHolderId, categoryId);
    }

    @Test
    void findAllCategory_noInput_returnCategory() {
        // Prepare
        Integer accountHolderId = 1;
        List<CategoryResponseDTO> responseDTOList = List.of(
                new CategoryResponseDTO(1L, "Electronics", "Category for electronic items"),
                new CategoryResponseDTO(2L, "Clothing", "Category for clothing items")
        );

        Mockito.when(categoryService.findAllCategory(accountHolderId)).thenReturn(responseDTOList);

        // Act
        ResponseEntity<List<CategoryResponseDTO>> responseEntity = categoryController.findAllCategory(accountHolderId);

        // Assert
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert Objects.requireNonNull(responseEntity.getBody()).size() == 2;
        assert responseEntity.getBody().containsAll(responseDTOList);
        Mockito.verify(categoryService).findAllCategory(accountHolderId);
    }

    @Test
    void searchCategories_validInput_returnCategory() {
        // Prepare
        Integer accountHolderId = 1;
        String name = "Electronics";
        List<CategoryResponseDTO> responseDTOList = List.of(
                new CategoryResponseDTO(1L, "Electronics", "Category for electronic items")
        );

        Mockito.when(categoryService.searchCategory(accountHolderId, name, null)).thenReturn(responseDTOList);

        // Act
        ResponseEntity<List<CategoryResponseDTO>> responseEntity = categoryController.searchCategories(accountHolderId, name, null);

        // Assert
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert Objects.requireNonNull(responseEntity.getBody()).size() == 1;
        assert responseEntity.getBody().getFirst().name().equals("Electronics");
    }
}
