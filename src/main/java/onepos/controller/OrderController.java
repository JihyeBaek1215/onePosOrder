package onepos.controller;

import org.springframework.web.bind.annotation.GetMapping;
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

	@PostMapping("/orders/{storeId}/{tableId}") // N건 조회 . 매장ID로 조회
	public String order(@PathVariable int storeId, @PathVariable int tableId, @RequestBody OrderItem orderItem)
	{
		orderService.createNewOrder(storeId, tableId, orderItem);
		// System.out.println(("OrderItem = "+orderItem.toString()));
		return "OK";
	}

	@GetMapping("/orders/{storeId}/{orderId}")
	public Order checkOrder(@PathVariable int storeId, @PathVariable int orderId){
		Order order = orderService.checkOrder(storeId, orderId);
		return order;
	}


}
