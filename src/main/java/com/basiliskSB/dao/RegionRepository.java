package com.basiliskSB.dao;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.basiliskSB.dto.region.RegionGridDTO;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.entity.Region;

public interface RegionRepository extends JpaRepository<Region, Long>{
	@Query("""
			SELECT new com.basiliskSB.dto.region.RegionGridDTO(reg.id, reg.city, reg.remark)
			FROM Region AS reg
			WHERE reg.city LIKE %:city% """)
	public List<RegionGridDTO> findAll(@Param("city") String city, Pageable pageable);
	
	@Query("""
			SELECT COUNT(reg.id)
			FROM Region AS reg
			WHERE reg.city LIKE %:city% """)
	public Long count(@Param("city") String city);
	
	@Query("""
			SELECT new com.basiliskSB.dto.utility.Dropdown(reg.id, reg.city)
			FROM Region AS reg
			ORDER By reg.city """)
	public List<Dropdown> findAllOrderByCity();
	
	@Query("""
			SELECT new com.basiliskSB.dto.region.RegionGridDTO(reg.id, reg.city, reg.remark)
			FROM Salesman AS sal
				INNER JOIN sal.regions AS reg
			WHERE sal.employeeNumber = :employeeNumber AND reg.city LIKE %:city% """)
	public List<RegionGridDTO> findAll(@Param("employeeNumber") String employeeNumber, @Param("city") String city, Pageable pageable);

	@Query("""
			SELECT COUNT(reg.id)
			FROM Salesman AS sal
				INNER JOIN sal.regions AS reg
			WHERE sal.employeeNumber = :employeeNumber AND reg.city LIKE %:city% """)
	public Long count(@Param("employeeNumber") String employeeNumber, @Param("city") String city);
	
	@Query("""
			SELECT COUNT(reg.id)
			FROM Salesman AS sal
				INNER JOIN sal.regions AS reg
			WHERE sal.employeeNumber = :employeeNumber AND reg.id = :regionId """)
	public Long count(@Param("employeeNumber") String employeeNumber, @Param("regionId") Long regionId);
}
