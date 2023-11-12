package com.basiliskSB.dao;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.basiliskSB.dto.supplier.SupplierGridDTO;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long>{
	@Query("""
			SELECT new com.basiliskSB.dto.supplier.SupplierGridDTO(sup.id, sup.companyName, sup.contactPerson, sup.jobTitle) 
			FROM Supplier AS sup
			WHERE sup.companyName LIKE %:companyName%
				AND sup.contactPerson LIKE %:contactPerson%
				AND sup.jobTitle LIKE %:jobTitle%
				AND sup.deleteDate IS NULL """)
	public List<SupplierGridDTO> findAll(@Param("companyName") String companyName,
			@Param("contactPerson") String contactPerson,
			@Param("jobTitle") String jobTitle,
			Pageable pageable);
	
	@Query("""
			SELECT COUNT(sup.id)
			FROM Supplier AS sup
			WHERE sup.companyName LIKE %:companyName%
				AND sup.contactPerson LIKE %:contactPerson%
				AND sup.jobTitle LIKE %:jobTitle%
				AND sup.deleteDate IS NULL """)
	public Long count(@Param("companyName") String companyName,
			@Param("contactPerson") String contactPerson,
			@Param("jobTitle") String jobTitle);
	
	@Query("""
			SELECT new com.basiliskSB.dto.utility.Dropdown(sup.id, sup.companyName)
			FROM Supplier AS sup
			WHERE sup.deleteDate IS NULL
			ORDER By sup.companyName """)
	public List<Dropdown> findAllOrderByCompanyName();
}
