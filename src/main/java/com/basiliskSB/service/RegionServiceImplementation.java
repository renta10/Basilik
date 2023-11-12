package com.basiliskSB.service;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.basiliskSB.dao.RegionRepository;
import com.basiliskSB.dao.SalesmanRepository;
import com.basiliskSB.dto.region.*;
import com.basiliskSB.dto.salesman.SalesmanGridDTO;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.entity.Region;
import com.basiliskSB.entity.Salesman;

@Service
public class RegionServiceImplementation implements RegionService {

	@Autowired
	private RegionRepository regionRepository;
	
	@Autowired
	private SalesmanRepository salesmanRepository;
	
	private final int rowsInPage = 10;
	
	@Override
	public List<RegionGridDTO> getRegionGrid(Integer page, String city) {
		Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
		List<RegionGridDTO> grid = regionRepository.findAll(city, pagination);
		return grid;
	}

	@Override
	public Long getTotalPages(String city) {
		double totalData = (double)(regionRepository.count(city));
		long totalPage = (long)(Math.ceil(totalData / rowsInPage));
		return totalPage;
	}

	@Override
	public UpsertRegionDTO getUpdateRegion(Long id) {
		Optional<Region> entityNullable = regionRepository.findById(id);
		Region entity = entityNullable.get();
		UpsertRegionDTO regionDTO = new UpsertRegionDTO(
					entity.getId(),
					entity.getCity(),
					entity.getRemark()
				);
		return regionDTO;
	}

	@Override
	public Long saveRegion(UpsertRegionDTO dto) {
		Region entity = new Region(
				dto.getId(),
				dto.getCity(),
				dto.getRemark()
			);
		Region respond = regionRepository.save(entity);
		return respond.getId();
	}

	@Override
	public void deleteRegion(Long id) {
		regionRepository.deleteById(id);
	}
	
	@Override
	public RegionHeaderDTO getRegionHeader(Long id) {
		Optional<Region> nullableEntity = regionRepository.findById(id);
		Region entity = nullableEntity.get();
		RegionHeaderDTO regionDTO = new RegionHeaderDTO(
					entity.getId(),
					entity.getCity(),
					entity.getRemark()
				);
		return regionDTO;
	}

	@Override
	public List<SalesmanGridDTO> getSalesmanGridByRegion(Long id, Integer page,
			String employeeNumber, String name, String employeeLevel, String superiorName) {
		Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
		List<SalesmanGridDTO> grid = salesmanRepository.findAll(id, employeeNumber, name, employeeLevel, superiorName, pagination);
		return grid;
	}

	@Override
	public Long getDetailTotalPages(Long id, String employeeNumber, String name, String employeeLevel, String superiorName) {
		double totalData = (double)(salesmanRepository.count(id, employeeNumber, name, employeeLevel, superiorName));
		long totalPage = (long)(Math.ceil(totalData / 10));
		return totalPage;
	}

	@Override
	public void assignSalesman(AssignSalesmanDTO dto) {
		Optional<Salesman> nullableSalesman = salesmanRepository.findById(dto.getSalesmanEmployeeNumber());
		Salesman salesman = nullableSalesman.get();
		Optional<Region> nullableRegion = regionRepository.findById(dto.getRegionId());
		Region region = nullableRegion.get();
		region.getSalesmen().add(salesman);
		regionRepository.save(region);
	}

	@Override
	public List<Dropdown> getSalesmanDropdown() {
		List<Dropdown> salesmanDropdown = salesmanRepository.findAllOrderByFirstName();
		return salesmanDropdown;
	}

	@Override
	public Boolean checkExistingRegionSalesman(Long regionId, String employeeNumber) {
		Long totalExistingRegionSalesman = regionRepository.count(employeeNumber, regionId);
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
