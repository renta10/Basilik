package com.basiliskSB.controller;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.basiliskSB.dto.product.*;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService service;	
	
	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue="") String name, 
			@RequestParam(required = false) Long categoryId,
			@RequestParam(required = false) Long supplierId,
			Model model){
		List<ProductGridDTO> grid = service.getProductGrid(page, name, categoryId, supplierId);
		long totalPages = service.getTotalPages(name, categoryId, supplierId);
		model.addAttribute("grid", grid);
		model.addAttribute("currentPage", page);
		model.addAttribute("name", name);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("supplierId", supplierId);
		List<Dropdown> categoryDropdown = service.getCategoryDropdown();
		model.addAttribute("categoryDropdown", categoryDropdown);
		List<Dropdown> supplierDropdown = service.getSupplierDropdown();
		model.addAttribute("supplierDropdown", supplierDropdown);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("breadCrumbs", "Product Index");
		return "product/product-index";
	}
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) Long id, Model model) {
		if (id != null) {
			UpsertProductDTO dto = service.getUpdateProduct(id);
			model.addAttribute("product", dto);
			model.addAttribute("type", "Update");
			List<Dropdown> categoryDropdown = service.getCategoryDropdown();
			model.addAttribute("categoryDropdown", categoryDropdown);
			List<Dropdown> supplierDropdown = service.getSupplierDropdown();
			model.addAttribute("supplierDropdown", supplierDropdown);
			model.addAttribute("breadCrumbs", "Product Index / Update Product");
		} else {
			UpsertProductDTO dto = new UpsertProductDTO();
			model.addAttribute("product", dto);
			model.addAttribute("type", "Insert");
			List<Dropdown> categoryDropdown = service.getCategoryDropdown();
			model.addAttribute("categoryDropdown", categoryDropdown);
			List<Dropdown> supplierDropdown = service.getSupplierDropdown();
			model.addAttribute("supplierDropdown", supplierDropdown);
			model.addAttribute("breadCrumbs", "Product Index / Insert Product");
		}
		return "product/product-form";
	}
	
	@PostMapping("/upsert")
	public String upsert(@Valid @ModelAttribute("product") UpsertProductDTO dto,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			List<Dropdown> categoryDropdown = service.getCategoryDropdown();
			model.addAttribute("categoryDropdown", categoryDropdown);
			List<Dropdown> supplierDropdown = service.getSupplierDropdown();
			model.addAttribute("supplierDropdown", supplierDropdown);
			if(dto.getId() != null) {
				model.addAttribute("type", "Update");
				model.addAttribute("breadCrumbs", "Product Index / Update Product");
			} else {
				model.addAttribute("type", "Insert");
				model.addAttribute("breadCrumbs", "Product Index / Insert Product");
			}
			return "product/product-form";
		} else {
			service.saveProduct(dto);
			return "redirect:/product/index";
		}
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(required = true) Long id, Model model) {
		long dependentOrderDetails = service.dependentOrderDetails(id);
		if(dependentOrderDetails > 0) {
			model.addAttribute("dependencies", dependentOrderDetails);
			model.addAttribute("breadCrumbs", "Product Index / Fail to Delete Product");
			return "product/product-delete";
		}
		service.deleteProduct(id);
		return "redirect:/product/index";
	}
}