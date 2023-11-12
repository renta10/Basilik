package com.basiliskSB.service;
import java.time.LocalDate;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.basiliskSB.dao.*;
import com.basiliskSB.dto.order.*;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.entity.Customer;
import com.basiliskSB.entity.Delivery;
import com.basiliskSB.entity.Order;
import com.basiliskSB.entity.OrderDetail;
import com.basiliskSB.entity.Product;
import com.basiliskSB.entity.Salesman;

@Service
public class OrderServiceImplementation implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private SalesmanRepository salesmanRepository;
	
	@Autowired
	private DeliveryRepository deliveryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	private final int rowsInPage = 10;
	
	@Override
	public List<OrderGridDTO> getOrderGrid(int page, String invoiceNumber, Long customerId, String employeeNumber,
			Long deliveryId, LocalDate orderDate) {
		Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("invoiceNumber"));
		List<OrderGridDTO> grid = orderRepository.findAll(
				invoiceNumber, customerId, employeeNumber, deliveryId, orderDate, pagination);
		return grid;
	}

	@Override
	public Long getTotalPages(String invoiceNumber, Long customerId, String employeeNumber, Long deliveryId,
			LocalDate orderDate) {
		double totalData = (double)(orderRepository.count(
				invoiceNumber, customerId, employeeNumber, deliveryId, orderDate));
		long totalPage = (long)(Math.ceil(totalData / rowsInPage));
		return totalPage;
	}

	@Override
	public UpdateOrderDTO getUpdateOrder(String invoiceNumber) {
		Optional<Order> entityNullable = orderRepository.findById(invoiceNumber);
		Order entity = entityNullable.get();
		UpdateOrderDTO orderDTO = new UpdateOrderDTO(
					entity.getInvoiceNumber(),
					entity.getCustomerId(),
					entity.getSalesEmployeeNumber(),
					entity.getOrderDate(),
					entity.getShippedDate(),
					entity.getDueDate(),
					entity.getDeliveryId(),
					entity.getDestinationAddress(),
					entity.getDestinationCity(),
					entity.getDestinationPostalCode()
				);
		return orderDTO;
	}

	@Override
	public String insertOrder(InsertOrderDTO dto) {
		Optional<Delivery> nullableDelivery = deliveryRepository.findById(dto.getDeliveryId());
		Delivery delivery = nullableDelivery.get();
		Double deliveryCost = delivery.getCost();
		Order entity = new Order(
				dto.getInvoiceNumber(),
				dto.getCustomerId(),
				dto.getSalesEmployeeNumber(),
				dto.getOrderDate(),
				dto.getShippedDate(),
				dto.getDueDate(),
				dto.getDeliveryId(),
				deliveryCost,
				dto.getDestinationAddress(),
				dto.getDestinationCity(),
				dto.getDestinationPostalCode()
			);
		Order respond = orderRepository.save(entity);
		return respond.getInvoiceNumber();
	}

	@Override
	public void updateOrder(UpdateOrderDTO dto) {
		Optional<Delivery> nullableDelivery = deliveryRepository.findById(dto.getDeliveryId());
		Delivery delivery = nullableDelivery.get();
		Double deliveryCost = delivery.getCost();
		Order entity = new Order(
				dto.getInvoiceNumber(),
				dto.getCustomerId(),
				dto.getSalesEmployeeNumber(),
				dto.getOrderDate(),
				dto.getShippedDate(),
				dto.getDueDate(),
				dto.getDeliveryId(),
				deliveryCost,
				dto.getDestinationAddress(),
				dto.getDestinationCity(),
				dto.getDestinationPostalCode()
			);
		orderRepository.save(entity);
	}

	@Override
	public void deleteOrder(String invoiceNumber) {
		orderRepository.deleteById(invoiceNumber);
	}

	@Override
	public List<OrderDetailGridDTO> getOrderDetailGrid(String invoiceNumber, int page) {
		List<OrderDetailGridDTO> grid = orderDetailRepository.findAll(invoiceNumber);
		for(OrderDetailGridDTO row : grid) {
			Double totalPrice = ((100 - row.getDiscount()) / 100) * (row.getQuantity().doubleValue() * row.getPrice());
			row.setTotalPrice(totalPrice);
		}
		return grid;
	}

	@Override
	public Long getDetailTotalPages(String invoiceNumber) {
		double totalData = (double)(orderDetailRepository.count(invoiceNumber));
		long totalPage = (long)(Math.ceil(totalData / rowsInPage));
		return totalPage;
	}

	@Override
	public UpsertOrderDetailDTO getUpdateOrderDetail(Long id) {
		Optional<OrderDetail> entityNullable = orderDetailRepository.findById(id);
		OrderDetail entity = entityNullable.get();
		UpsertOrderDetailDTO orderDetailDTO = new UpsertOrderDetailDTO(
					entity.getId(),
					entity.getInvoiceNumber(),
					entity.getProductId(),
					entity.getQuantity(),
					entity.getDiscount()
				);
		return orderDetailDTO;
	}

	@Override
	public Long saveOrderDetail(UpsertOrderDetailDTO dto) {
		Optional<Product> nullableEntity = productRepository.findById(dto.getProductId());
		Double unitPrice = nullableEntity.get().getPrice();
		OrderDetail entity = new OrderDetail(
				dto.getId(),
				dto.getInvoiceNumber(),
				dto.getProductId(),
				unitPrice,
				dto.getQuantity(),
				dto.getDiscount()
			);
		OrderDetail respond = orderDetailRepository.save(entity);
		return respond.getId();
	}

	@Override
	public void deleteOrderDetail(Long id) {
		orderDetailRepository.deleteById(id);
	}

	@Override
	public List<Dropdown> getCustomerDropdown() {
		List<Dropdown> dropdowns = customerRepository.findAllOrderByCompanyName();
		return dropdowns;
	}

	@Override
	public List<Dropdown> getSalesmanDropdown() {
		List<Dropdown> dropdowns = salesmanRepository.findAllOrderByFirstName();
		return dropdowns;
	}

	@Override
	public List<Dropdown> getDeliveryDropdown() {
		List<Dropdown> dropdowns = deliveryRepository.findAllOrderByCompanyName();
		return dropdowns;
		
	}

	@Override
	public List<Dropdown> getProductDropdown() {
		List<Dropdown> dropdowns = productRepository.findAllOrderByName();
		return dropdowns;
	}

	@Override
	public OrderHeaderDTO getOrderHeader(String invoiceNumber) {
		Optional<Order> nullableEntity = orderRepository.findById(invoiceNumber);
		Order entity = nullableEntity.get();
		Optional<Customer> nullableCustomer = customerRepository.findById(entity.getCustomerId());
		Customer customer = nullableCustomer.get();
		Optional<Salesman> nullableSalesman = salesmanRepository.findById(entity.getSalesEmployeeNumber());
		Salesman salesman = nullableSalesman.get();
		OrderHeaderDTO orderDTO = new OrderHeaderDTO(
					invoiceNumber,
					customer.getAddress(),
					String.format("%s %s", salesman.getFirstName(), salesman.getLastName()),
					entity.getOrderDate()
				);
		return orderDTO;
	}

	@Override
	public Boolean checkExistingOrder(String invoiceNumber) {
		Long totalExistingOrder = orderRepository.countByInvoiceNumber(invoiceNumber);
		return (totalExistingOrder > 0) ? true : false;
	}

}
