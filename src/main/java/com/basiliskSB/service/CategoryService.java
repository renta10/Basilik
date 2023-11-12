package com.basiliskSB.service;
import java.util.*;
import com.basiliskSB.dto.category.*;

public interface CategoryService {
	public List<CategoryGridDTO> getCategoryGrid(int page, String name);
	public Long getTotalPages(String name);
	public UpsertCategoryDTO getUpdateCategory(long id);
	public Long saveCategory(UpsertCategoryDTO dto);
	public Boolean deleteCategory(long id);
	public Long dependentProducts(long id);
	public Boolean checkExistingCategoryName(Long id, String name);
}
