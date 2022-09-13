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
	public int createNewOrder(int storeId, int tableId, String holeflag, OrderItem orderItem){
		Order order = new Order();
		order.setStatus(OrderStatus.orderRequest);
   	order.setOrderItems(orderItem);
		order.setStoreId(storeId);
		order.setTableNo(tableId);
		order.setHoleflag(holeflag);
		int orderId = orderRepository.save(order).getId();
		return orderId;

	}

	@Transactional(readOnly = true)
	public Order checkOrder(int storeId, int orderId){
		Order order = orderRepository.findById(orderId);
		return order;
	}

	@Transactional
	public Order cancelOrder(int orderId){
		Order order = orderRepository.findById(orderId);
		order.setStatus(OrderStatus.cancelRequest);
		orderRepository.save(order);
		return order;
	}

}
