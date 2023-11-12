package com.basiliskSB.service;
import java.time.LocalDate;
import java.util.*;
import com.basiliskSB.dto.order.*;
import com.basiliskSB.dto.utility.Dropdown;

public interface OrderService {
	public List<OrderGridDTO> getOrderGrid(int page, String invoiceNumber, Long customerId, String employeeNumber, 
			Long deliveryId, LocalDate orderDate);
	public Long getTotalPages(String invoiceNumber, Long customerId, String employeeNumber, 
			Long deliveryId, LocalDate orderDate);
	public UpdateOrderDTO getUpdateOrder(String invoiceNumber);
	public String insertOrder(InsertOrderDTO dto);
	public void updateOrder(UpdateOrderDTO dto);
	public void deleteOrder(String invoiceNumber);
	public List<OrderDetailGridDTO> getOrderDetailGrid(String invoiceNumber, int page);
	public OrderHeaderDTO getOrderHeader(String invoiceNumber);
	public Long getDetailTotalPages(String invoiceNumber);
	public UpsertOrderDetailDTO getUpdateOrderDetail(Long id);
	public Long saveOrderDetail(UpsertOrderDetailDTO dto);
	public void deleteOrderDetail(Long id);
	public Boolean checkExistingOrder(String invoiceNumber);
	public List<Dropdown> getCustomerDropdown();
	public List<Dropdown> getSalesmanDropdown();
	public List<Dropdown> getDeliveryDropdown();
	public List<Dropdown> getProductDropdown();
}
