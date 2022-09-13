package onepos.domain;

import javax.persistence.Embedded;

import lombok.Data;
import onepos.AbstractEvent;

@Data
public class Cooking extends AbstractEvent {

	 private int id;
    private int orderId;
    private int storeId;
    private String status;
    private OrderStatus orderStatus;
    private String nextStep;

    @Embedded
    OrderItem orderItems = new OrderItem();

}

