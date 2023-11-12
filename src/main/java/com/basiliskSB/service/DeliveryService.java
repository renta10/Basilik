package com.basiliskSB.service;
import java.util.List;
import com.basiliskSB.dto.delivery.*;

public interface DeliveryService {
	public List<DeliveryGridDTO> getDeliveryGrid(int page, String company);
	public Long getTotalPages(String company);
	public UpsertDeliveryDTO getUpdateDelivery(long id);
	public Long saveDelivery(UpsertDeliveryDTO dto);
	public Boolean deleteDelivery(long id);
	public Long dependentOrders(long id);
	public Boolean checkExistingDeliveryName(Long id, String company);
}
