package com.basiliskSB.controller;
import java.util.*;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.*;
import com.basiliskSB.service.CategoryService;
import com.basiliskSB.dto.category.*;

@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String name, Model model){
		List<CategoryGridDTO> grid = service.getCategoryGrid(page, name);
		long totalPages = service.getTotalPages(name);
		model.addAttribute("grid", grid);
		model.addAttribute("currentPage", page);
		model.addAttribute("name", name);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("breadCrumbs", "Category Index");
		return "category/category-index";
	}
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) Long id, Model model) {
		if (id != null) {
			UpsertCategoryDTO dto = service.getUpdateCategory(id);
			model.addAttribute("category", dto);
			model.addAttribute("type", "Update");
			model.addAttribute("breadCrumbs", "Category Index / Update Category");
		} else {
			UpsertCategoryDTO dto = new UpsertCategoryDTO();
			model.addAttribute("category", dto);
			model.addAttribute("type", "Insert");
			model.addAttribute("breadCrumbs", "Category Index / Insert Category");
		}
		return "category/category-form";
	}
	
	@PostMapping("/upsert")
	public String upsert(@Valid @ModelAttribute("category") UpsertCategoryDTO dto,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			if(dto.getId() != null) {
				model.addAttribute("type", "Update");
				model.addAttribute("breadCrumbs", "Category Index / Update Category");
			} else {
				model.addAttribute("type", "Insert");
				model.addAttribute("breadCrumbs", "Category Index / Insert Category");
			}
			return "category/category-form";
		} else {
			service.saveCategory(dto);
			return "redirect:/category/index";
		}
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(required = true) Long id, Model model) {
		long dependentProducts = service.dependentProducts(id);
		if(dependentProducts > 0) {
			model.addAttribute("dependencies", dependentProducts);
			model.addAttribute("breadCrumbs", "Category Index / Fail to Delete Category");
			return "category/category-delete";
		}
		service.deleteCategory(id);
		return "redirect:/category/index";
	}
}
