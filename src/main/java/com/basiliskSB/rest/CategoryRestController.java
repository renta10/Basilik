package com.basiliskSB.rest;
import java.util.*;
import com.basiliskSB.dto.category.UpsertCategoryDTO;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.basiliskSB.dto.category.CategoryGridDTO;
import com.basiliskSB.service.CategoryService;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryRestController {
	
	@Autowired
	private CategoryService service;

	@Operation(summary="Mendapatkan data category untuk index grid.", description = "Data akan di respond dalam jumlah 10 per halaman.")
    @ApiResponse(responseCode = "200", description = "Category Grid",
		content = {@Content(
			mediaType = "application/json",
			schema = @Schema( implementation = CategoryGridDTO.class)
		)}
	)
	@GetMapping(value={
			"",
			"/page={page}",
			"/name={name}",
			"/page={page}&name={name}"
	})
	public ResponseEntity<Object> get(@PathVariable(required = false) Integer page, @PathVariable(required = false) String name){
		page = (page == null) ? 1 : page;
		name = (name == null) ? "" : name;
		try{
			List<CategoryGridDTO> categories = service.getCategoryGrid(page, name);
			return ResponseEntity.status(HttpStatus.OK).body(categories);
		} catch (Exception exception){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
		}

	}


	@Operation(summary = "Mendapatkan jumlah total halaman category yang ada setelah filter.", description = "Jumlah halaman dari data category")
	@ApiResponse(responseCode = "200", description = "Total Number of Page",
		content = {@Content(
				mediaType = "application/json",
				schema = @Schema( implementation = Long.class)
		)}
	)
	@GetMapping(value={
			"/pages",
			"/pages/name={name}"
	})
	public ResponseEntity<Object> getTotalPage(@PathVariable(required = false) String name){
		name = (name == null) ? "" : name;
		try{
			Long totalPages = service.getTotalPages(name);
			return ResponseEntity.status(HttpStatus.OK).body(totalPages);
		} catch (Exception exception){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
		}
	}


	@Operation(summary = "Mendapatkan satu category untuk update form.", description = "Satu data category berdasarkan id nya")
	@ApiResponse(responseCode = "200", description = "Total Number of Page",
		content = {@Content(
				mediaType = "application/json",
				schema = @Schema( implementation = UpsertCategoryDTO.class)
		)}
	)
	@GetMapping("/{id}")
	public ResponseEntity<Object> getUpdate(@PathVariable(required = true) Long id){
		try{
			UpsertCategoryDTO dto = service.getUpdateCategory(id);
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		} catch (Exception exception){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
		}
	}

	@Operation(summary = "Menambahkan category baru", description = "Response akan mengembalikan informasi id yang baru saja ditambahkan")
	@ApiResponse(responseCode = "200", description = "Total Number of Page",
			content = {@Content(
					mediaType = "application/json",
					schema = @Schema( implementation = UpsertCategoryDTO.class)
			)}
	)
	@PostMapping
	public ResponseEntity<Object> post(@Valid @RequestBody UpsertCategoryDTO dto, BindingResult bindingResult){
		try{
			if(!bindingResult.hasErrors()){
				dto.setId(0l);
				Long respondId = service.saveCategory(dto);
				dto.setId(respondId);
				return ResponseEntity.status(HttpStatus.CREATED).body(dto);
			} else {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
			}
		} catch (Exception exception){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
		}
	}


	@Operation(summary = "Mengupdate category yang sudah ada atau menambahkannya", description = "Response akan mengembalikan informasi data yang baru saja diupdate.")
	@ApiResponse(responseCode = "200", description = "Total Number of Page",
			content = {@Content(
					mediaType = "application/json",
					schema = @Schema( implementation = UpsertCategoryDTO.class)
			)}
	)
	@PutMapping
	public ResponseEntity<Object> put(@Valid @RequestBody UpsertCategoryDTO dto, BindingResult bindingResult){
		try{
			if(!bindingResult.hasErrors()){
				service.saveCategory(dto);
				return ResponseEntity.status(HttpStatus.OK).body(dto);
			} else {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
			}
		}catch (Exception exception){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
		}
	}


	@Operation(summary = "Menghapus category berdasarkan id nya.", description = "Response akan mengembalikan informasi berupa id dari data yang barus saja dihapus.")
	@ApiResponse(responseCode = "200", description = "Total Number of Page",
			content = {@Content(
					mediaType = "application/json",
					schema = @Schema( implementation = Long.class)
			)}
	)
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable(required = true) Long id){
		try{
			service.deleteCategory(id);
			return ResponseEntity.status(HttpStatus.OK).body(id);
		}catch (Exception exception){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
		}
	}
}
