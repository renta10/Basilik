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
import com.basiliskSB.dto.customer.*;
import com.basiliskSB.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue="") String company, 
			@RequestParam(defaultValue="") String contact,
			Model model){
		List<CustomerGridDTO> grid = service.getCustomerGrid(page, company, contact);
		long totalPages = service.getTotalPages(company, contact);
		model.addAttribute("grid", grid);
		model.addAttribute("currentPage", page);
		model.addAttribute("company", company);
		model.addAttribute("contact", contact);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("breadCrumbs", "Customer Index");
		return "customer/customer-index";
	}
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) Long id, Model model) {
		if (id != null) {
			UpsertCustomerDTO dto = service.getUpdateCustomer(id);
			model.addAttribute("customer", dto);
			model.addAttribute("type", "Update");
			model.addAttribute("breadCrumbs", "Customer Index / Update Customer");
		} else {
			UpsertCustomerDTO dto = new UpsertCustomerDTO();
			model.addAttribute("customer", dto);
			model.addAttribute("type", "Insert");
			model.addAttribute("breadCrumbs", "Customer Index / Insert Customer");
		}
		return "customer/customer-form";
	}
	
	@PostMapping("/upsert")
	public String upsert(@Valid @ModelAttribute("customer") UpsertCustomerDTO dto,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			if(dto.getId() != null) {
				model.addAttribute("type", "Update");
				model.addAttribute("breadCrumbs", "Customer Index / Update Customer");
			} else {
				model.addAttribute("type", "Insert");
				model.addAttribute("breadCrumbs", "Customer Index / Insert Customer");
			}
			return "customer/customer-form";
		} else {
			service.saveCustomer(dto);
			return "redirect:/customer/index";
		}
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(required = true) Long id, Model model) {
		service.deleteCustomer(id);
		return "redirect:/customer/index";
	}
	
}
