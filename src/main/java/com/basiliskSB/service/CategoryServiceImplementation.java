package com.basiliskSB.service;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.*;
import com.basiliskSB.dao.*;
import com.basiliskSB.dto.category.*;
import com.basiliskSB.entity.Category;

@Service
public class CategoryServiceImplementation implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	private final int rowsInPage = 10;
	
	@Override
	public List<CategoryGridDTO> getCategoryGrid(int page, String name) {
		Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
		List<CategoryGridDTO> grid = categoryRepository.findAll(name, pagination);
		return grid;
	}
	
	@Override
	public Long getTotalPages(String name) {
		double totalData = (double)(categoryRepository.count(name));
		long totalPage = (long)(Math.ceil(totalData / rowsInPage));
		return totalPage;
	}

	@Override
	public UpsertCategoryDTO getUpdateCategory(long id) {
		Optional<Category> nullableEntity = categoryRepository.findById(id);
		Category entity = nullableEntity.get();
		UpsertCategoryDTO categoryDTO = new UpsertCategoryDTO(
				entity.getId(),
				entity.getName(),
				entity.getDescription()
			);
		return categoryDTO;
	}
	
	@Override
	public Long saveCategory(UpsertCategoryDTO dto) {
		Category entity = new Category(
					dto.getId(),
					dto.getName(),
					dto.getDescription()
				);
		Category respond = categoryRepository.save(entity);
		return respond.getId();
	}

	@Override
	public Boolean deleteCategory(long id) {
		long totalDependentProducts = dependentProducts(id);
		if(totalDependentProducts == 0) {
			categoryRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Long dependentProducts(long id) {
		long totalDependentProducts = productRepository.countByCategoryId(id);
		return totalDependentProducts;
	}
	
	@Override
	public Boolean checkExistingCategoryName(Long id, String name) {
		id = (id == null) ? 0l : id;
		long totalData = categoryRepository.count(id, name);
		if(totalData > 0) {
			return true;
		}
		return false;
	}
}
