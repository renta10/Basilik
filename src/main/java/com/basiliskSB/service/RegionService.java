package com.basiliskSB.service;
import java.util.List;
import com.basiliskSB.dto.region.*;
import com.basiliskSB.dto.salesman.*;
import com.basiliskSB.dto.utility.Dropdown;

public interface RegionService {
	public List<RegionGridDTO> getRegionGrid(Integer page, String city);
	public Long getTotalPages(String city);
	public UpsertRegionDTO getUpdateRegion(Long id);
	public Long saveRegion(UpsertRegionDTO dto);
	public void deleteRegion(Long id);
	public RegionHeaderDTO getRegionHeader(Long id);
	public List<SalesmanGridDTO> getSalesmanGridByRegion(Long id, Integer page, 
			String employeeNumber, String name, String employeeLevel, String superiorName);
	public Long getDetailTotalPages(Long id, String employeeNumber, String name, String employeeLevel, String superiorName);
	public void assignSalesman(AssignSalesmanDTO dto);
	public List<Dropdown> getSalesmanDropdown();
	public Boolean checkExistingRegionSalesman(Long regionId, String employeeNumber);
	public void detachRegionSalesman(Long regionId, String employeeNumber);
}
