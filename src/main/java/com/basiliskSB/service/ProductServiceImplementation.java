package com.basiliskSB.service;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.basiliskSB.dao.ProductRepository;
import com.basiliskSB.dao.CategoryRepository;
import com.basiliskSB.dao.OrderDetailRepository;
import com.basiliskSB.dao.SupplierRepository;
import com.basiliskSB.dto.product.*;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.entity.Product;

@Service
public class ProductServiceImplementation implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	private final int rowsInPage = 10;
	
	@Override
	public List<ProductGridDTO> getProductGrid(int page, String name, Long categoryId, Long supplierId) {
		Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
		List<ProductGridDTO> grid = productRepository.findAll(name, categoryId, supplierId, pagination);
		return grid;
	}

	@Override
	public Long getTotalPages(String name, Long categoryId, Long supplierId) {
		double totalData = (double)(productRepository.count(name, categoryId, supplierId));
		long totalPage = (long)(Math.ceil(totalData / rowsInPage));
		return totalPage;
	}

	@Override
	public UpsertProductDTO getUpdateProduct(long id) {
		Optional<Product> nullableEntity = productRepository.findById(id);
		Product entity = nullableEntity.get();
		UpsertProductDTO productDTO = new UpsertProductDTO(
					entity.getId(),
					entity.getName(),
					entity.getSupplierId(),
					entity.getCategoryId(),
					entity.getDescription(),
					entity.getPrice(),
					entity.getStock(),
					entity.getOnOrder(),
					entity.getDiscontinue()
				);
		return productDTO;
	}

	@Override
	public Long saveProduct(UpsertProductDTO dto) {
		Product entity = new Product(
				dto.getId(),
				dto.getName(),
				dto.getSupplierId(),
				dto.getCategoryId(),
				dto.getDescription(),
				dto.getPrice(),
				dto.getStock(),
				dto.getOnOrder(),
				dto.getDiscontinue()
			);
		Product respond = productRepository.save(entity);
		return respond.getId();
	}

	@Override
	public Boolean deleteProduct(long id) {
		long totalDependentOrderDetails = dependentOrderDetails(id);
		if(totalDependentOrderDetails == 0) {
			productRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Long dependentOrderDetails(long id) {
		long totalDependentOrderDetails = orderDetailRepository.countByProductId(id);
		return totalDependentOrderDetails;
	}

	@Override
	public List<Dropdown> getCategoryDropdown() {
		List<Dropdown> dropdowns = categoryRepository.findAllOrderByName();
		return dropdowns;
	}

	@Override
	public List<Dropdown> getSupplierDropdown() {
		List<Dropdown> dropdowns = supplierRepository.findAllOrderByCompanyName();
		return dropdowns;
	}

}
