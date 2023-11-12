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

import com.basiliskSB.dto.region.*;
import com.basiliskSB.dto.salesman.SalesmanGridDTO;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.service.RegionService;

@Controller
@RequestMapping("/region")
public class RegionController {

	@Autowired
	private RegionService service;
	
	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue="") String city, 
			Model model){
		List<RegionGridDTO> grid = service.getRegionGrid(page, city);
		long totalPages = service.getTotalPages(city);
		model.addAttribute("grid", grid);
		model.addAttribute("currentPage", page);
		model.addAttribute("city", city);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("breadCrumbs", "Region Index");
		return "region/region-index";
	}
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) Long id, Model model) {
		if (id != null) {
			UpsertRegionDTO dto = service.getUpdateRegion(id);
			model.addAttribute("region", dto);
			model.addAttribute("type", "Update");
			model.addAttribute("breadCrumbs", "Region Index / Update Region");
		} else {
			UpsertRegionDTO dto = new UpsertRegionDTO();
			model.addAttribute("region", dto);
			model.addAttribute("type", "Insert");
			model.addAttribute("breadCrumbs", "Region Index / Insert Region");
		}
		return "region/region-form";
	}
	
	@PostMapping("/upsert")
	public String upsert(@Valid @ModelAttribute("region") UpsertRegionDTO dto,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			if(dto.getId() != null) {
				model.addAttribute("type", "Update");
				model.addAttribute("breadCrumbs", "Region Index / Update Region");
			} else {
				model.addAttribute("type", "Insert");
				model.addAttribute("breadCrumbs", "Region Index / Insert Region");
			}
			return "region/region-form";
		} else {
			service.saveRegion(dto);
			return "redirect:/region/index";
		}
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(required = true) Long id, Model model) {
		service.deleteRegion(id);
		return "redirect:/region/index";
	}
	
	@GetMapping("/detail")
	public String detail(@RequestParam(required = true) Long id,
			@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue="") String employeeNumber,
			@RequestParam(defaultValue="") String name,
			@RequestParam(defaultValue="") String employeeLevel,
			@RequestParam(defaultValue="") String superiorName,
			Model model) {
		RegionHeaderDTO header = service.getRegionHeader(id);
		List<SalesmanGridDTO> grid = service.getSalesmanGridByRegion(id, page, employeeNumber, name, employeeLevel, superiorName);
		long totalPages = service.getDetailTotalPages(id, employeeNumber, name, employeeLevel, superiorName);
		List<Dropdown> employeeLevelDropdown = Dropdown.getEmployeeLevelDropdown();
		String breadCrumbs = String.format("Region Index / Salesman of %s", header.getCity());
		model.addAttribute("headerId", header.getId());
		model.addAttribute("headerCity", header.getCity());
		model.addAttribute("headerRemark", header.getRemark());
		model.addAttribute("grid", grid);
		model.addAttribute("currentPage", page);
		model.addAttribute("employeeNumber", employeeNumber);
		model.addAttribute("name", name);
		model.addAttribute("employeeLevel", employeeLevel);
		model.addAttribute("superiorName", superiorName);
		model.addAttribute("employeeLevelDropdown", employeeLevelDropdown);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("breadCrumbs", breadCrumbs);
		return "region/region-detail";
	}
	
	@GetMapping("/assignDetailForm")
	public String assignDetailForm(@RequestParam(required = true) Long id, Model model) {
		AssignSalesmanDTO dto = new AssignSalesmanDTO();
		dto.setRegionId(id);
		RegionHeaderDTO header = service.getRegionHeader(id);
		String breadCrumbs = String.format("Region Index / Salesman of %s / Assign Salesman", header.getCity());
		List<Dropdown> salesmanDropdown = service.getSalesmanDropdown();
		model.addAttribute("regionSalesman", dto);
		model.addAttribute("salesmanDropdown", salesmanDropdown);
		model.addAttribute("breadCrumbs", breadCrumbs);
		return "region/region-detail-form";
	}
	
	@PostMapping("/assignDetail")
	public String assignDetail(@Valid @ModelAttribute("regionSalesman") AssignSalesmanDTO dto,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			RegionHeaderDTO header = service.getRegionHeader(dto.getRegionId());
			List<Dropdown> salesmanDropdown = service.getSalesmanDropdown();
			String breadCrumbs = String.format("Region Index / Salesman of %s / Assign Salesman", header.getCity());
			model.addAttribute("salesmanDropdown", salesmanDropdown);
			model.addAttribute("breadCrumbs", breadCrumbs);
			return "region/region-detail-form";
		} else {
			service.assignSalesman(dto);
			redirectAttributes.addAttribute("id", dto.getRegionId());
			return "redirect:/region/detail";
		}
	}
	
	@GetMapping("/deleteDetail")
	public String deleteDetail(@RequestParam(required = true) Long regionId,
			@RequestParam(required = true) String employeeNumber,
			Model model, RedirectAttributes redirectAttributes) {
		service.detachRegionSalesman(regionId, employeeNumber);
		redirectAttributes.addAttribute("id", regionId);
		return "redirect:/region/detail";
	}
}
