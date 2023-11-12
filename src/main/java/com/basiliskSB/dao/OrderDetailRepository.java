package com.basiliskSB.dao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.basiliskSB.dto.order.OrderDetailGridDTO;
import com.basiliskSB.entity.OrderDetail;


public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

	@Query("""
			SELECT new com.basiliskSB.dto.order.OrderDetailGridDTO(ordet.id, prod.name, ordet.unitPrice, ordet.quantity, ordet.discount) 
			FROM OrderDetail AS ordet
				INNER JOIN ordet.product AS prod
			WHERE ordet.invoiceNumber = :invoiceNumber""")
	public List<OrderDetailGridDTO> findAll(@Param("invoiceNumber") String invoiceNumber);
	
	@Query("""
			SELECT COUNT(ordet.id) 
			FROM OrderDetail AS ordet
			WHERE ordet.invoiceNumber = :invoiceNumber""")
	public Long count(@Param("invoiceNumber") String invoiceNumber);
	
	@Query("""
			SELECT COUNT(ordet.id) 
			FROM OrderDetail AS ordet
			WHERE ordet.productId = :productId""")
	public Long countByProductId(@Param("productId") Long productId);
}
