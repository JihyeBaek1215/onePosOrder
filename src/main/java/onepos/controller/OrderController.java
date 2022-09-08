package onepos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;
import onepos.domain.Order;
import onepos.domain.OrderItem;
import onepos.service.OrderService;


@RequiredArgsConstructor
@RestController
public class OrderController {

	private final OrderService orderService;

	//주문
	@PostMapping("/orders/{storeId}/{tableId}/{holeflag}")
	public int order(@PathVariable int storeId, @PathVariable int tableId,@PathVariable String holeflag, @RequestBody OrderItem orderItem)
	{
		int orderId = orderService.createNewOrder(storeId, tableId, holeflag, orderItem);
		return orderId;
	}

	// 주문조회
	@GetMapping("/orders/{storeId}/{orderId}")
	public Order checkOrder(@PathVariable int storeId, @PathVariable int orderId){
		Order order = orderService.checkOrder(storeId, orderId);
		return order;
	}

	// 주문취소
	@PatchMapping("/orders/cancel/{orderId}")
	public Order cancelOrder(@PathVariable int orderId){
		Order order = orderService.cancelOrder(orderId);
		return order;
	}


}
