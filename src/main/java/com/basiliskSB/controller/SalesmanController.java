package com.basiliskSB.controller;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.basiliskSB.dto.region.RegionGridDTO;
import com.basiliskSB.dto.salesman.*;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.service.SalesmanService;

@Controller
@RequestMapping("/salesman")
public class SalesmanController {

	@Autowired
	private SalesmanService service;
	
	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue="") String employeeNumber,
			@RequestParam(defaultValue="") String name,
			@RequestParam(defaultValue="") String employeeLevel,
			@RequestParam(defaultValue="") String superiorName,
			Model model){
		List<SalesmanGridDTO> grid = service.getSalesmanGrid(page, employeeNumber, name, employeeLevel, superiorName);
		long totalPages = service.getTotalPages(employeeNumber, name, employeeLevel, superiorName);
		List<Dropdown> employeeLevelDropdown = Dropdown.getEmployeeLevelDropdown();
		model.addAttribute("employeeLevelDropdown", employeeLevelDropdown);
		model.addAttribute("grid", grid);
		model.addAttribute("currentPage", page);
		model.addAttribute("employeeNumber", employeeNumber);
		model.addAttribute("name", name);
		model.addAttribute("employeeLevel", employeeLevel);
		model.addAttribute("superiorName", superiorName);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("breadCrumbs", "Salesman Index");
		return "salesman/salesman-index";
	}
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) String employeeNumber, Model model) {
		List<Dropdown> employeeLevelDropdown = Dropdown.getEmployeeLevelDropdown();
		model.addAttribute("employeeLevelDropdown", employeeLevelDropdown);
		List<Dropdown> superiorDropdown = service.getSuperiorDropdown();
		model.addAttribute("superiorDropdown", superiorDropdown);
		if (employeeNumber != null) {
			UpdateSalesmanDTO dto = service.getUpdateSalesman(employeeNumber);
			model.addAttribute("salesman", dto);
			model.addAttribute("type", "update");
			model.addAttribute("breadCrumbs", "Salesman Index / Update Salesman");
		} else {
			InsertSalesmanDTO dto = new InsertSalesmanDTO();
			model.addAttribute("salesman", dto);
			model.addAttribute("type", "insert");
			model.addAttribute("breadCrumbs", "Salesman Index / Insert Salesman");
		}
		return "salesman/salesman-form";
	}
	
	@PostMapping("/insert")
	public String insert(@Valid @ModelAttribute("salesman") InsertSalesmanDTO dto,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("type", "insert");
			model.addAttribute("breadCrumbs", "Salesman Index / Insert Salesman");
			List<Dropdown> employeeLevelDropdown = Dropdown.getEmployeeLevelDropdown();
			model.addAttribute("employeeLevelDropdown", employeeLevelDropdown);
			List<Dropdown> superiorDropdown = service.getSuperiorDropdown();
			model.addAttribute("superiorDropdown", superiorDropdown);
			return "salesman/salesman-form";
		} else {
			service.insertSalesman(dto);
			return "redirect:/salesman/index";
		}
	}
	
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("salesman") UpdateSalesmanDTO dto,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("type", "Update");
			model.addAttribute("breadCrumbs", "Salesman Index / Update Salesman");
			List<Dropdown> employeeLevelDropdown = Dropdown.getEmployeeLevelDropdown();
			model.addAttribute("employeeLevelDropdown", employeeLevelDropdown);
			List<Dropdown> superiorDropdown = service.getSuperiorDropdown();
			model.addAttribute("superiorDropdown", superiorDropdown);
			return "salesman/salesman-form";
		} else {
			service.updateSalesman(dto);
			return "redirect:/salesman/index";
		}
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(required = true) String employeeNumber, Model model) {
		long dependentOrders = service.dependentOrders(employeeNumber);
		long dependentSubordinates = service.dependentSubordinates(employeeNumber);
		if(dependentOrders > 0 || dependentSubordinates > 0) {
			model.addAttribute("dependentOrders", dependentOrders);
			model.addAttribute("dependentSubordinates", dependentSubordinates);
			model.addAttribute("breadCrumbs", "Salesman Index / Fail to Delete Salesman");
			return "salesman/salesman-delete";
		}		
		service.deleteSalesman(employeeNumber);
		return "redirect:/salesman/index";
	}
	
	@GetMapping("/detail")
	public String detail(@RequestParam(required = true) String employeeNumber,
			@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue="") String city,
			Model model) {
		SalesmanHeaderDTO header = service.getSalesmanHeader(employeeNumber);
		List<RegionGridDTO> grid = service.getRegionGridBySalesman(employeeNumber, page, city);
		long totalPages = service.getDetailTotalPages(employeeNumber, city);
		String breadCrumbs = String.format("Salesman Index / Region of %s", header.getFullName());
		model.addAttribute("headerEmployeeNumber", header.getEmployeeNumber());
		model.addAttribute("headerFullName", header.getFullName());
		model.addAttribute("grid", grid);
		model.addAttribute("currentPage", page);
		model.addAttribute("city", city);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("breadCrumbs", breadCrumbs);
		return "salesman/salesman-detail";
	}
	
	@GetMapping("/assignDetailForm")
	public String assignDetailForm(@RequestParam(required = true) String employeeNumber, Model model) {
		AssignRegionDTO dto = new AssignRegionDTO();
		dto.setSalesmanEmployeeNumber(employeeNumber);
		SalesmanHeaderDTO header = service.getSalesmanHeader(employeeNumber);
		String breadCrumbs = String.format("Salesman Index / Region of %s / Assign Region", header.getFullName());
		List<Dropdown> regionDropdown = service.getRegionDropdown();
		model.addAttribute("regionSalesman", dto);
		model.addAttribute("regionDropdown", regionDropdown);
		model.addAttribute("breadCrumbs", breadCrumbs);
		return "salesman/salesman-detail-form";
	}
	
	@PostMapping("/assignDetail")
	public String assignDetail(@Valid @ModelAttribute("regionSalesman") AssignRegionDTO dto,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			SalesmanHeaderDTO header = service.getSalesmanHeader(dto.getSalesmanEmployeeNumber());
			List<Dropdown> regionDropdown = service.getRegionDropdown();
			String breadCrumbs = String.format("Salesman Index / Region of %s / Assign Region", header.getFullName());
			model.addAttribute("regionSalesman", dto);
			model.addAttribute("regionDropdown", regionDropdown);
			model.addAttribute("breadCrumbs", breadCrumbs);
			return "salesman/salesman-detail-form";
		} else {
			service.assignRegion(dto);
			redirectAttributes.addAttribute("employeeNumber", dto.getSalesmanEmployeeNumber());
			return "redirect:/salesman/detail";
		}
	}
	
	@GetMapping("/deleteDetail")
	public String deleteDetail(@RequestParam(required = true) Long regionId,
			@RequestParam(required = true) String employeeNumber,
			Model model, RedirectAttributes redirectAttributes) {
		service.detachRegionSalesman(regionId, employeeNumber);
		redirectAttributes.addAttribute("employeeNumber", employeeNumber);
		return "redirect:/salesman/detail";
	}
}
