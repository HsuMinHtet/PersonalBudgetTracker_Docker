package edu.miu.cs489.hsumin.personalbudgettracker.mapper;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.CategoryRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.CategoryResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
   // @Mapping(source ="categoryRequestDTO.addressRequestDTO" ,target = "address")
    Category categoryRequestDTOToCategory(CategoryRequestDTO categoryRequestDTO);
   // @Mapping(source = "accountHolder.address", target = "categoryResponseDTO")
    CategoryResponseDTO categoryToCategoryResponseDTO(Category category);
}
