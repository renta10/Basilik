package com.basiliskSB.controller;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import com.basiliskSB.service.OrderService;
import com.basiliskSB.dto.order.*;
import com.basiliskSB.dto.utility.Dropdown;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService service;
	
	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue="") String invoiceNumber, 
			@RequestParam(required=false) Long customerId, 
			@RequestParam(required=false) String employeeNumber, 
			@RequestParam(required=false) Long deliveryId, 
			@RequestParam(required=false) String orderDate,
			Model model) {	
		LocalDate formattedDate = null;
		if(orderDate != null && orderDate != "") {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			formattedDate = LocalDate.parse(orderDate, formatter);
		}
		List<OrderGridDTO> grid = service.getOrderGrid(page, invoiceNumber, customerId, employeeNumber, deliveryId, formattedDate);
		long totalPages = service.getTotalPages(invoiceNumber, customerId, employeeNumber, deliveryId, formattedDate);
		List<Dropdown> customerDropdown = service.getCustomerDropdown();
		List<Dropdown> salesmanDropdown = service.getSalesmanDropdown();
		List<Dropdown> deliveryDropdown = service.getDeliveryDropdown();
		model.addAttribute("customerDropdown", customerDropdown);
		model.addAttribute("salesmanDropdown", salesmanDropdown);
		model.addAttribute("deliveryDropdown", deliveryDropdown);
		model.addAttribute("grid", grid);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("breadCrumbs", "Order Index");
		model.addAttribute("invoiceNumber", invoiceNumber);
		model.addAttribute("customerId", customerId);
		model.addAttribute("employeeNumber", employeeNumber);
		model.addAttribute("deliveryId", deliveryId);
		model.addAttribute("orderDate", orderDate);
		return "order/order-index";
	}
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) String invoiceNumber, Model model) {
		List<Dropdown> customerDropdown = service.getCustomerDropdown();
		List<Dropdown> salesmanDropdown = service.getSalesmanDropdown();
		List<Dropdown> deliveryDropdown = service.getDeliveryDropdown();
		model.addAttribute("customerDropdown", customerDropdown);
		model.addAttribute("salesmanDropdown", salesmanDropdown);
		model.addAttribute("deliveryDropdown", deliveryDropdown);
		if(invoiceNumber != null) {
			UpdateOrderDTO dto = service.getUpdateOrder(invoiceNumber);
			model.addAttribute("order", dto);
			model.addAttribute("type", "update");
			model.addAttribute("breadCrumbs", "Order Index / Update Order");
		} else {
			InsertOrderDTO dto = new InsertOrderDTO();
			model.addAttribute("order", dto);
			model.addAttribute("type", "insert");
			model.addAttribute("breadCrumbs", "Order Index / Insert Order");
		}
		return "order/order-form";
	}
	
	@PostMapping("/insert")
	public String insert(@Valid @ModelAttribute("order") InsertOrderDTO dto, 
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			List<Dropdown> customerDropdown = service.getCustomerDropdown();
			List<Dropdown> salesmanDropdown = service.getSalesmanDropdown();
			List<Dropdown> deliveryDropdown = service.getDeliveryDropdown();
			model.addAttribute("customerDropdown", customerDropdown);
			model.addAttribute("salesmanDropdown", salesmanDropdown);
			model.addAttribute("deliveryDropdown", deliveryDropdown);
			model.addAttribute("type", "insert");
			model.addAttribute("breadCrumbs", "Order Index / Insert Order");
			return "order/order-form";
		} else {
			service.insertOrder(dto);
			return "redirect:/order/index";
		}
	}
	
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("order") UpdateOrderDTO dto,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			List<Dropdown> customerDropdown = service.getCustomerDropdown();
			List<Dropdown> salesmanDropdown = service.getSalesmanDropdown();
			List<Dropdown> deliveryDropdown = service.getDeliveryDropdown();
			model.addAttribute("customerDropdown", customerDropdown);
			model.addAttribute("salesmanDropdown", salesmanDropdown);
			model.addAttribute("deliveryDropdown", deliveryDropdown);
			model.addAttribute("type", "update");
			model.addAttribute("breadCrumbs", "Order Index / Update Order");
			return "order/order-form";
		} else {
			service.updateOrder(dto);
			return "redirect:/order/index";
		}
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(required = true) String invoiceNumber, Model model) {
		service.deleteOrder(invoiceNumber);
		return "redirect:/order/index";
	}
	
	@GetMapping("/detail")
	public String detail(@RequestParam(required = true) String invoiceNumber,
			@RequestParam(defaultValue = "1") Integer page,
			Model model) {
		OrderHeaderDTO header = service.getOrderHeader(invoiceNumber);
		List<OrderDetailGridDTO> grid = service.getOrderDetailGrid(invoiceNumber, page);
		long totalPages = service.getDetailTotalPages(invoiceNumber);
		String breadCrumbs = String.format("Order Index / Order of %s", invoiceNumber);
		model.addAttribute("headerInvoiceNumber", invoiceNumber);
		model.addAttribute("headerCustomer", header.getCustomer());
		model.addAttribute("headerSalesman", header.getSalesman());
		model.addAttribute("headerOrderDate", header.getOrderDate());
		model.addAttribute("grid", grid);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("breadCrumbs", breadCrumbs);
		return "order/order-detail";
	}
	
	@GetMapping("/upsertDetailForm")
	public String upsertDetailForm(@RequestParam(required = false) Long id, @RequestParam(required = false) String invoiceNumber, Model model) {
		List<Dropdown> productDropdown = service.getProductDropdown();
		model.addAttribute("productDropdown", productDropdown);
		if(id != null) {
			UpsertOrderDetailDTO dto = service.getUpdateOrderDetail(id);
			model.addAttribute("orderDetail", dto);
			model.addAttribute("type", "Update");
			model.addAttribute("breadCrumbs", 
					String.format("Order Index / Order of %s / Insert Detail", dto.getInvoiceNumber()));
		} else {
			UpsertOrderDetailDTO dto = new UpsertOrderDetailDTO();
			dto.setInvoiceNumber(invoiceNumber);
			model.addAttribute("orderDetail", dto);
			model.addAttribute("type", "Insert");
			model.addAttribute("breadCrumbs", 
					String.format("Order Index / Order of %s / Insert Detail", invoiceNumber));
		}
		return "order/order-detail-form";
	}
	
	@PostMapping("/upsertDetail")
	public String upsertDetail(@Valid @ModelAttribute("orderDetail") UpsertOrderDetailDTO dto,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		List<Dropdown> productDropdown = service.getProductDropdown();
		model.addAttribute("productDropdown", productDropdown);
		if(bindingResult.hasErrors()) {
			if(dto.getId() != null) {
				model.addAttribute("type", "Update");
				model.addAttribute("breadCrumbs",
						String.format("Order Index / Order of %s / Update Detail", dto.getInvoiceNumber()));
			} else {
				model.addAttribute("type", "Insert");
				model.addAttribute("breadCrumbs", 
						String.format("Order Index / Order of %s / Insert Detail", dto.getInvoiceNumber()));
			}
			return "order/order-detail-form";
		} else {
			service.saveOrderDetail(dto);
			redirectAttributes.addAttribute("invoiceNumber", dto.getInvoiceNumber());
			return "redirect:/order/detail/";
		}
	}
	
	@GetMapping("/deleteDetail")
	public String deleteDetail(@RequestParam(required = true) Long id, Model model, RedirectAttributes redirectAttributes) {
		String invoiceNumber = service.getUpdateOrderDetail(id).getInvoiceNumber();
		service.deleteOrderDetail(id);
		redirectAttributes.addAttribute("invoiceNumber", invoiceNumber);
		return "redirect:/order/detail/";
	}
}
