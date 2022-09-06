package onepos.service;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import onepos.domain.Order;
import onepos.domain.OrderItem;
import onepos.domain.OrderRepository;
import onepos.domain.OrderStatus;


@RequiredArgsConstructor
@Service
public class OrderService {

	private final OrderRepository orderRepository;

	@Transactional
	public void createNewOrder(int storeId, int tableId, OrderItem orderItem){
		Order order = new Order();
		order.setStatus(OrderStatus.orderRequest);
   	order.setOrderItems(orderItem);
		order.setStoreId(storeId);
		order.setTableNo(tableId);
		orderRepository.save(order);
	}

	@Transactional(readOnly = true)
	public Order checkOrder(int storeId, int orderId){
		Order order = orderRepository.findByStoreIdAndId(storeId,orderId);
		return order;
	}
	// @Transactional(readOnly = true) // 변경감지 자체를 수행안한다. select하는곳에는 다 붙혀줘야함
	// public List<MenuRespDto> 메뉴조회 (int id) {


	// 	List<Menu> menuEntity = menuRepository.searchStore(id);


	// 	List<MenuRespDto> MenuRespDtos = new ArrayList<>();
	//     for (Menu menu : menuEntity) {
	//     	MenuRespDtos.add(new MenuRespDto(menu));
	// 	}


	// 	return MenuRespDtos;
	// }

}
