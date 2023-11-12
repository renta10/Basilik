package com.basiliskSB.dao;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.basiliskSB.dto.product.ProductGridDTO;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	@Query("""
			SELECT new com.basiliskSB.dto.product.ProductGridDTO(pro.id, pro.name, sup.companyName, cat.name, pro.price) 
			FROM Product AS pro
				INNER JOIN pro.category AS cat
				LEFT JOIN pro.supplier AS sup
			WHERE pro.name LIKE %:name%
				AND (:categoryId IS NULL OR pro.categoryId = :categoryId)
				AND (:supplierId IS NULL OR pro.supplierId = :supplierId) """)
	public List<ProductGridDTO> findAll(@Param("name") String name, 
			@Param("categoryId") Long categoryId, @Param("supplierId") Long supplierId, Pageable pageable);
	
	@Query("""
			SELECT COUNT(pro.id)
			FROM Product AS pro
			WHERE pro.name LIKE %:name%
				AND (:categoryId IS NULL OR pro.categoryId = :categoryId)
				AND (:supplierId IS NULL OR pro.supplierId = :supplierId) """)
	public Long count(@Param("name") String name, 
			@Param("categoryId") Long categoryId, @Param("supplierId") Long supplierId);
	
	@Query("""
			SELECT COUNT(pro.id)
			FROM Product AS pro
			WHERE pro.categoryId = :categoryId """)
	public Long countByCategoryId(@Param("categoryId") Long categoryId);
	
	@Query("""
			SELECT new com.basiliskSB.dto.utility.Dropdown(pro.id, pro.name) 
			FROM Product AS pro
			ORDER By pro.name """)
	public List<Dropdown> findAllOrderByName();
}
