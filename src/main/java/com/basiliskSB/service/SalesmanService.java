package com.basiliskSB.service;
import java.util.List;
import com.basiliskSB.dto.region.*;
import com.basiliskSB.dto.salesman.*;
import com.basiliskSB.dto.utility.Dropdown;

public interface SalesmanService {
	public List<SalesmanGridDTO> getSalesmanGrid(Integer page, String employeeNumber, String name, String employeeLevel, String superiorName);
	public Long getTotalPages(String employeeNumber, String name, String employeeLevel, String superiorName);
	public UpdateSalesmanDTO getUpdateSalesman(String employeeNumber);
	public String insertSalesman(InsertSalesmanDTO dto);
	public void updateSalesman(UpdateSalesmanDTO dto);
	public List<Dropdown> getSuperiorDropdown();
	public void deleteSalesman(String employeeNumber);
	public Long dependentOrders(String employeeNumber);
	public Long dependentSubordinates(String superiorEmployeeNumber);
	public Boolean checkExistingSalesman(String employeeNumber);
	public SalesmanHeaderDTO getSalesmanHeader(String employeeNumber);
	public List<RegionGridDTO> getRegionGridBySalesman(String employeeNumber, Integer page, String city);
	public Long getDetailTotalPages(String employeeNumber,  String city);
	public void assignRegion(AssignRegionDTO dto);
	public List<Dropdown> getRegionDropdown();
	public Boolean checkExistingRegionSalesman(Long regionId, String employeeNumber);
	public void detachRegionSalesman(Long regionId, String employeeNumber);
}
