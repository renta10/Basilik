package com.basiliskSB.service;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.basiliskSB.dao.CustomerRepository;
import com.basiliskSB.dto.customer.*;
import com.basiliskSB.entity.Customer;

@Service
public class CustomerServiceImplementation implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	private final int rowsInPage = 10;
	
	@Override
	public List<CustomerGridDTO> getCustomerGrid(int page, String company, String contact) {
		Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
		List<CustomerGridDTO> grid = customerRepository.findAll(company, contact, pagination);
		return grid;
	}

	@Override
	public Long getTotalPages(String company, String contact) {
		double totalData = (double)(customerRepository.count(company, contact));
		long totalPage = (long)(Math.ceil(totalData / rowsInPage));
		return totalPage;
	}

	@Override
	public UpsertCustomerDTO getUpdateCustomer(long id) {
		Optional<Customer> nullableEntity = customerRepository.findById(id);
		Customer entity = nullableEntity.get();
		UpsertCustomerDTO customerDTO = new UpsertCustomerDTO(
				entity.getId(),
				entity.getCompanyName(),
				entity.getContactPerson(),
				entity.getAddress(),
				entity.getCity(),
				entity.getPhone(),
				entity.getEmail()
			);
		return customerDTO;
	}

	@Override
	public Long saveCustomer(UpsertCustomerDTO dto) {
		Customer entity = new Customer(
				dto.getId(),
				dto.getCompanyName(),
				dto.getContactPerson(),
				dto.getAddress(),
				dto.getCity(),
				dto.getPhone(),
				dto.getEmail()
			);
		Customer respond = customerRepository.save(entity);
		return respond.getId();
	}

	@Override
	public void deleteCustomer(long id) {
		customerRepository.deleteById(id);
	}
}
