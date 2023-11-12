package com.basiliskSB.service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.basiliskSB.dao.SupplierRepository;
import com.basiliskSB.dto.supplier.*;
import com.basiliskSB.entity.Supplier;

@Service
public class SupplierServiceImplementation implements SupplierService {

	@Autowired
	private SupplierRepository supplierRepository;
	
	private final int rowsInPage = 10;
	
	@Override
	public List<SupplierGridDTO> getSupplierGrid(int page, String company, String contact, String jobTitle) {
		Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
		List<SupplierGridDTO> grid = supplierRepository.findAll(company, contact, jobTitle, pagination);
		return grid;
	}

	@Override
	public Long getTotalPages(String company, String contact, String jobTitle) {
		double totalData = (double)(supplierRepository.count(company, contact, jobTitle));
		long totalPage = (long)(Math.ceil(totalData / rowsInPage));
		return totalPage;
	}

	@Override
	public UpsertSupplierDTO getUpdateSupplier(long id) {

		Optional<Supplier> nullableEntity = supplierRepository.findById(id);
		Supplier entity = nullableEntity.get();
		UpsertSupplierDTO supplierDTO = new UpsertSupplierDTO(
				entity.getId(),
				entity.getCompanyName(),
				entity.getContactPerson(),
				entity.getJobTitle(),
				entity.getAddress(),
				entity.getCity(),
				entity.getPhone(),
				entity.getEmail()
			);
		return supplierDTO;
	}

	@Override
	public Long saveSupplier(UpsertSupplierDTO dto) {
		Supplier entity = new Supplier(
				dto.getId(),
				dto.getCompanyName(),
				dto.getContactPerson(),
				dto.getJobTitle(),
				dto.getAddress(),
				dto.getCity(),
				dto.getPhone(),
				dto.getEmail()
			);
		Supplier respond = supplierRepository.save(entity);
		return respond.getId();
	}

	@Override
	public void deleteSupplier(long id) {
		Optional<Supplier> nullableEntity = supplierRepository.findById(id);
		Supplier entity = nullableEntity.get();
		entity.setDeleteDate(LocalDateTime.now());
		supplierRepository.save(entity);
	}

}
