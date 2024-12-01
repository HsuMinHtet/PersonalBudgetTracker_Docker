package edu.miu.cs489.hsumin.personalbudgettracker.controller;

import edu.miu.cs489.hsumin.personalbudgettracker.dto.requestDTO.CategoryRequestDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.dto.responseDTO.CategoryResponseDTO;
import edu.miu.cs489.hsumin.personalbudgettracker.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {

    private final CategoryService categoryService;

    //AccountHolder (Create category)
    @PreAuthorize("hasRole('ACCOUNT_HOLDER')")
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createNewCategory(@RequestBody CategoryRequestDTO categoryRequestDTO){
        Optional<CategoryResponseDTO> categoryResponseDTO= categoryService.createCategory(categoryRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponseDTO.get());
    }
    //AccountHolder (Update category)
    @PreAuthorize("hasRole('ACCOUNT_HOLDER')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO>updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryRequestDTO categoryRequestDTO
    ){
        Optional<CategoryResponseDTO> categoryResponseDTO= categoryService.updateCategory(id, categoryRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponseDTO.get());
    }
    //AccountHolder (Delete category)
    @PreAuthorize("hasRole('ACCOUNT_HOLDER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@RequestHeader Integer accountHolder_id, @PathVariable Long id){
        categoryService.deleteCategory(accountHolder_id, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //AccountHolder (Find categoryById)
    @PreAuthorize("hasRole('ACCOUNT_HOLDER')")
    @GetMapping("/{id}")
    private ResponseEntity<CategoryResponseDTO> findCategoryById(@PathVariable Long id){
        Optional<CategoryResponseDTO> categoryResponseDTO= categoryService.findByCategoryID(id);
        if(categoryResponseDTO.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(categoryResponseDTO.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    //AccountHolder (Find All category)
    @PreAuthorize("hasRole('ACCOUNT_HOLDER')")
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> findAllCategory(@RequestHeader Integer accountHolder_id){
        List<CategoryResponseDTO> categoryResponseDTOS= categoryService.findAllCategory(accountHolder_id);
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponseDTOS);
    }

    //AccountHolder(Multiple Criteria)
    @PreAuthorize("hasRole('ACCOUNT_HOLDER')")
    @GetMapping("/criteria")
    public ResponseEntity<List<CategoryResponseDTO>> searchCategories(
            @RequestHeader Integer accountHolder_id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description
    ) {
        List<CategoryResponseDTO> categoryResponseDTOS= categoryService.searchCategory(accountHolder_id,name,description);
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponseDTOS);
    }
}
