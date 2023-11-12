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
import com.basiliskSB.dto.delivery.*;
import com.basiliskSB.service.DeliveryService;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {
	
	@Autowired
	private DeliveryService service;
	
	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue="") String company, 
			Model model){ 
		List<DeliveryGridDTO> grid = service.getDeliveryGrid(page, company);
		long totalPages = service.getTotalPages(company);
		model.addAttribute("grid", grid);
		model.addAttribute("currentPage", page);
		model.addAttribute("company", company);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("breadCrumbs", "Delivery Index");
		return "delivery/delivery-index";
	}
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) Long id, Model model) {
		if (id != null) {
			UpsertDeliveryDTO dto = service.getUpdateDelivery(id);
			model.addAttribute("delivery", dto);
			model.addAttribute("type", "Update");
			model.addAttribute("breadCrumbs", "Delivery Index / Update Delivery");
		} else {
			UpsertDeliveryDTO dto = new UpsertDeliveryDTO();
			model.addAttribute("delivery", dto);
			model.addAttribute("type", "Insert");
			model.addAttribute("breadCrumbs", "Delivery Index / Insert Delivery");
		}
		return "delivery/delivery-form";
	}
	
	@PostMapping("/upsert")
	public String upsert(@Valid @ModelAttribute("delivery") UpsertDeliveryDTO dto,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			if(dto.getId() != null) {
				model.addAttribute("type", "Update");
				model.addAttribute("breadCrumbs", "Delivery Index / Update Delivery");
			} else {
				model.addAttribute("type", "Insert");
				model.addAttribute("breadCrumbs", "Delivery Index / Insert Delivery");
			}
			return "delivery/delivery-form";
		} else {
			service.saveDelivery(dto);
			return "redirect:/delivery/index";
		}
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(required = true) Long id, Model model) {
		long dependentOrders = service.dependentOrders(id);
		if(dependentOrders > 0) {
			model.addAttribute("dependencies", dependentOrders);
			model.addAttribute("breadCrumbs", "Delivery Index / Fail to Delete Delivery");
			return "delivery/delivery-delete";
		}
		service.deleteDelivery(id);
		return "redirect:/delivery/index";
	}
}
