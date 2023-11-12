package com.basiliskSB.service;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.basiliskSB.dao.DeliveryRepository;
import com.basiliskSB.dao.OrderRepository;
import com.basiliskSB.dto.delivery.*;
import com.basiliskSB.entity.Delivery;

@Service
public class DeliveryServiceImplementation implements DeliveryService {

	@Autowired
	private DeliveryRepository deliveryRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	private final int rowsInPage = 10;
	
	@Override
	public List<DeliveryGridDTO> getDeliveryGrid(int page, String company) {
		Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
		List<DeliveryGridDTO> grid = deliveryRepository.findAll(company, pagination);
		return grid;
	}

	@Override
	public Long getTotalPages(String company) {
		double totalData = (double)(deliveryRepository.count(company));
		long totalPage = (long)(Math.ceil(totalData / rowsInPage));
		return totalPage;
	}

	@Override
	public UpsertDeliveryDTO getUpdateDelivery(long id) {
		Optional<Delivery> nullableEntity = deliveryRepository.findById(id);
		Delivery entity = nullableEntity.get();
		UpsertDeliveryDTO deliveryDTO = new UpsertDeliveryDTO(
					entity.getId(),
					entity.getCompanyName(),
					entity.getPhone(),
					entity.getCost()
				);
		return deliveryDTO;
	}

	@Override
	public Long saveDelivery(UpsertDeliveryDTO dto) {
		Delivery entity = new Delivery(
				dto.getId(),
				dto.getCompanyName(),
				dto.getPhone(),
				dto.getCost()
			);
		Delivery respond = deliveryRepository.save(entity);
		return respond.getId();
	}

	@Override
	public Boolean deleteDelivery(long id) {
		long totalDependentOrders = dependentOrders(id);
		if(totalDependentOrders == 0) {
			deliveryRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Long dependentOrders(long id) {
		long totalDependentOrders = orderRepository.countByDeliveryId(id);
		return totalDependentOrders;
	}

	@Override
	public Boolean checkExistingDeliveryName(Long id, String company) {
		id = (id == null) ? 0l : id;
		Long totalExistingDelivery = deliveryRepository.count(id, company);
		return (totalExistingDelivery > 0) ? true : false;
	}

}
