package com.basiliskSB.dao;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.basiliskSB.dto.category.CategoryGridDTO;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	@Query("""
			SELECT new com.basiliskSB.dto.category.CategoryGridDTO(cat.id, cat.name, cat.description) 
			FROM Category AS cat
			WHERE cat.name LIKE %:name%	""")
	public List<CategoryGridDTO> findAll(@Param("name") String name, Pageable pageable);
	
	@Query("""
			SELECT COUNT(cat.id)
			FROM Category AS cat
			WHERE cat.name LIKE %:name%	""")
	public Long count(@Param("name") String name);
	
	@Query("""
			SELECT COUNT(cat.id)
			FROM Category AS cat
			WHERE (:id = 0l AND cat.name = :name) OR 
				(cat.id = :id AND cat.name = :name)	""")
	public Long count(@Param("id") Long id, @Param("name") String name);
	
	@Query("""
			SELECT new com.basiliskSB.dto.utility.Dropdown(cat.id, cat.name)
			FROM Category AS cat
			ORDER By cat.name """)
	public List<Dropdown> findAllOrderByName();
}
