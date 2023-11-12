package com.basiliskSB.service;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.basiliskSB.dao.OrderRepository;
import com.basiliskSB.dao.RegionRepository;
import com.basiliskSB.dao.SalesmanRepository;
import com.basiliskSB.dto.region.*;
import com.basiliskSB.dto.salesman.*;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.entity.Region;
import com.basiliskSB.entity.Salesman;

@Service
public class SalesmanServiceImplementation implements SalesmanService {

	@Autowired
	private SalesmanRepository salesmanRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private RegionRepository regionRepository;
	
	private final int rowsInPage = 10;
	
	@Override
	public List<SalesmanGridDTO> getSalesmanGrid(Integer page, String employeeNumber, String name, String employeeLevel, String superiorName) {
		Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
		List<SalesmanGridDTO> grid = salesmanRepository.findAll(employeeNumber, name, employeeLevel, superiorName, pagination);
		return grid;
	}

	@Override
	public Long getTotalPages(String employeeNumber, String name, String employeeLevel, String superiorName) {
		double totalData = (double)(salesmanRepository.count(employeeNumber, name, employeeLevel, superiorName));
		long totalPage = (long)(Math.ceil(totalData / 10));
		return totalPage;
	}

	@Override
	public UpdateSalesmanDTO getUpdateSalesman(String employeeNumber) {
		Optional<Salesman> nullableEntity = salesmanRepository.findById(employeeNumber);
		Salesman entity = nullableEntity.get();
		UpdateSalesmanDTO salesmanDTO = new UpdateSalesmanDTO(
				entity.getEmployeeNumber(),
				entity.getFirstName(),
				entity.getLastName(),
				entity.getLevel(),
				entity.getBirthDate(),
				entity.getHiredDate(),
				entity.getAddress(),
				entity.getCity(),
				entity.getPhone(),
				entity.getSuperiorEmployeeNumber()
			);
		return salesmanDTO;
	}

	@Override
	public String insertSalesman(InsertSalesmanDTO dto) {
		String superiorEmployeeNumber = null;
		if(!dto.getSuperiorEmployeeNumber().equals("")) {
			superiorEmployeeNumber = dto.getSuperiorEmployeeNumber();
		}
		Salesman entity = new Salesman(
				dto.getEmployeeNumber(),
				dto.getFirstName(),
				dto.getLastName(),
				dto.getLevel(),
				dto.getBirthDate(),
				dto.getHiredDate(),
				dto.getAddress(),
				dto.getCity(),
				dto.getPhone(),
				superiorEmployeeNumber
			);
		Salesman respond = salesmanRepository.save(entity);
		return respond.getEmployeeNumber();
	}

	@Override
	public void updateSalesman(UpdateSalesmanDTO dto) {
		String superiorEmployeeNumber = null;
		if(!dto.getSuperiorEmployeeNumber().equals("")) {
			superiorEmployeeNumber = dto.getSuperiorEmployeeNumber();
		}
		Salesman entity = new Salesman(
				dto.getEmployeeNumber(),
				dto.getFirstName(),
				dto.getLastName(),
				dto.getLevel(),
				dto.getBirthDate(),
				dto.getHiredDate(),
				dto.getAddress(),
				dto.getCity(),
				dto.getPhone(),
				superiorEmployeeNumber
			);
		salesmanRepository.save(entity);
	}

	@Override
	public void deleteSalesman(String employeeNumber) {
		salesmanRepository.deleteById(employeeNumber);
	}

	@Override
	public Long dependentOrders(String employeeNumber) {
		long totalDependentOrders = orderRepository.countByEmployeeNumber(employeeNumber);
		return totalDependentOrders;
	}
	
	@Override
	public Long dependentSubordinates(String superiorEmployeeNumber) {
		long totalDependentSubordinates = salesmanRepository.countBySuperiorEmployeeNumber(superiorEmployeeNumber);
		return totalDependentSubordinates;
	}
	
	@Override
	public SalesmanHeaderDTO getSalesmanHeader(String employeeNumber) {
		Optional<Salesman> nullableEntity = salesmanRepository.findById(employeeNumber);
		Salesman entity = nullableEntity.get();
		SalesmanHeaderDTO salesmanDTO = new SalesmanHeaderDTO(
					employeeNumber,
					String.format("%s %s", entity.getFirstName(), entity.getLastName())
				);
		return salesmanDTO;	
	}

	@Override
	public List<RegionGridDTO> getRegionGridBySalesman(String employeeNumber, Integer page, String city) {
		Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
		List<RegionGridDTO> grid = regionRepository.findAll(employeeNumber, city, pagination);
		return grid;
	}

	@Override
	public Long getDetailTotalPages(String employeeNumber, String city) {
		double totalData = (double)(regionRepository.count(employeeNumber, city));
		long totalPage = (long)(Math.ceil(totalData / rowsInPage));
		return totalPage;
	}

	@Override
	public List<Dropdown> getSuperiorDropdown() {
		List<Dropdown> dropdowns = salesmanRepository.findAllOrderByFirstName();
		return dropdowns;
	}

	@Override
	public Boolean checkExistingSalesman(String employeeNumber) {	
		Long totalExistingSalesman = salesmanRepository.count(employeeNumber);
		return (totalExistingSalesman > 0) ? true : false;
	}

	@Override
	public void assignRegion(AssignRegionDTO dto) {
		Optional<Salesman> nullableSalesman = salesmanRepository.findById(dto.getSalesmanEmployeeNumber());
		Salesman salesman = nullableSalesman.get();
		Optional<Region> nullableRegion = regionRepository.findById(dto.getRegionId());
		Region region = nullableRegion.get();
		salesman.getRegions().add(region);
		salesmanRepository.save(salesman);
	}

	@Override
	public List<Dropdown> getRegionDropdown() {
		List<Dropdown> regionDropdown = regionRepository.findAllOrderByCity();
		return regionDropdown;
	}

	@Override
	public Boolean checkExistingRegionSalesman(Long regionId, String employeeNumber) {
		Long totalExistingRegionSalesman = salesmanRepository.count(regionId, employeeNumber);
		return (totalExistingRegionSalesman > 0) ? true : false;
	}

	@Override
	public void detachRegionSalesman(Long regionId, String employeeNumber) {
		Optional<Salesman> nullableSalesman = salesmanRepository.findById(employeeNumber);
		Salesman salesman = nullableSalesman.get();
		Optional<Region> nullableRegion = regionRepository.findById(regionId);
		Region region = nullableRegion.get();
		region.getSalesmen().remove(salesman);
		regionRepository.save(region);
	}

}
