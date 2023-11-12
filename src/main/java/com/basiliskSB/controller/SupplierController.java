package com.basiliskSB.controller;
import java.util.*;
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
import com.basiliskSB.dto.supplier.*;
import com.basiliskSB.service.SupplierService;

@Controller
@RequestMapping("/supplier")
public class SupplierController {
	
	@Autowired
	private SupplierService service;
	
	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue="") String company,
			@RequestParam(defaultValue="") String contact,
			@RequestParam(defaultValue="") String jobTitle,
			Model model){
		List<SupplierGridDTO> grid = service.getSupplierGrid(page, company, contact, jobTitle);
		long totalPages = service.getTotalPages(company, contact, jobTitle);
		model.addAttribute("grid", grid);
		model.addAttribute("currentPage", page);
		model.addAttribute("company", company);
		model.addAttribute("contact", contact);
		model.addAttribute("jobTitle", jobTitle);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("breadCrumbs", "Supplier Index");
		return "supplier/supplier-index";
	}
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) Long id, Model model) {
		if (id != null) {
			UpsertSupplierDTO dto = service.getUpdateSupplier(id);
			model.addAttribute("supplier", dto);
			model.addAttribute("type", "Update");
			model.addAttribute("breadCrumbs", "Supplier Index / Update Supplier");
		} else {
			UpsertSupplierDTO dto = new UpsertSupplierDTO();
			model.addAttribute("supplier", dto);
			model.addAttribute("type", "Insert");
			model.addAttribute("breadCrumbs", "Supplier Index / Insert Supplier");
		}
		return "supplier/supplier-form";
	}
	
	@PostMapping("/upsert")
	public String upsert(@Valid @ModelAttribute("supplier") UpsertSupplierDTO dto,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			if(dto.getId() != null) {
				model.addAttribute("type", "Update");
				model.addAttribute("breadCrumbs", "Supplier Index / Update Supplier");
			} else {
				model.addAttribute("type", "Insert");
				model.addAttribute("breadCrumbs", "Supplier Index / Insert Supplier");
			}
			return "supplier/supplier-form";
		} else {
			service.saveSupplier(dto);
			return "redirect:/supplier/index";
		}
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(required = true) Long id, Model model) {
		service.deleteSupplier(id);
		return "redirect:/supplier/index";
	}
}
