package com.basiliskSB.service;
import java.util.List;
import com.basiliskSB.dto.customer.*;

public interface CustomerService {
	public List<CustomerGridDTO> getCustomerGrid(int page, String company, String contact);
	public Long getTotalPages(String company, String contact);
	public UpsertCustomerDTO getUpdateCustomer(long id);
	public Long saveCustomer(UpsertCustomerDTO dto);
	public void deleteCustomer(long id);
}
