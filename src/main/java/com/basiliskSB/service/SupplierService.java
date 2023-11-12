package com.basiliskSB.service;
import java.util.List;
import com.basiliskSB.dto.supplier.*;

public interface SupplierService {
	public List<SupplierGridDTO> getSupplierGrid(int page, String company, String contact, String jobTitle);
	public Long getTotalPages(String company, String contact, String jobTitle);
	public UpsertSupplierDTO getUpdateSupplier(long id);
	public Long saveSupplier(UpsertSupplierDTO dto);
	public void deleteSupplier(long id);
}
