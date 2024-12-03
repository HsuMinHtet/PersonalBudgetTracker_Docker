package edu.miu.cs489.hsumin.personalbudgettracker.mapper;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.CategoryRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.CategoryResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.model.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-02T16:11:50-0600",
    comments = "version: 1.6.2, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category categoryRequestDTOToCategory(CategoryRequestDTO categoryRequestDTO) {
        if ( categoryRequestDTO == null ) {
            return null;
        }

        Category category = new Category();

        category.setName( categoryRequestDTO.name() );
        category.setDescription( categoryRequestDTO.description() );

        return category;
    }

    @Override
    public CategoryResponseDTO categoryToCategoryResponseDTO(Category category) {
        if ( category == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;

        id = category.getId();
        name = category.getName();
        description = category.getDescription();

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO( id, name, description );

        return categoryResponseDTO;
    }
}
