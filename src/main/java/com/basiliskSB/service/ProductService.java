package com.basiliskSB.service;
import java.util.List;
import com.basiliskSB.dto.product.*;
import com.basiliskSB.dto.utility.Dropdown;

public interface ProductService {
	public List<ProductGridDTO> getProductGrid(int page, String name, Long categoryId, Long supplierId);
	public Long getTotalPages(String name, Long categoryId, Long supplierId);
	public UpsertProductDTO getUpdateProduct(long id);
	public Long saveProduct(UpsertProductDTO dto);
	public Boolean deleteProduct(long id);
	public Long dependentOrderDetails(long id);
	public List<Dropdown> getCategoryDropdown();
	public List<Dropdown> getSupplierDropdown();
}
