package edu.miu.cs489.hsumin.personalbudgettracker.service;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.CategoryRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.CategoryResponseDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<CategoryResponseDTO> createCategory(CategoryRequestDTO categoryRequestDTO);

    Optional<CategoryResponseDTO> findByCategoryID(Long id);

    Optional<CategoryResponseDTO> updateCategory(Long id, CategoryRequestDTO categoryRequestDTO);

    List<CategoryResponseDTO> findAllCategory(Integer accountHolder_id);

    List<CategoryResponseDTO> searchCategory(Integer accountHolder_id,String name, String description);

    void deleteCategory(Integer accountHolder_id, Long id);

}
