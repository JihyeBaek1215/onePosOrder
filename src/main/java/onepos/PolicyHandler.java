package onepos;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import onepos.config.kafka.KafkaProcessor;
import onepos.domain.Order;
import onepos.domain.OrderCancelled;
import onepos.domain.OrderRepository;
import onepos.domain.OrderStatus;
import onepos.domain.Paid;
import onepos.domain.Refunded;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    OrderRepository orderRepository;

    // 계산 완료되어 조리 중으로 넘어감
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaid(@Payload Paid paid){

         if(paid.isMe()){
            Order order = orderRepository.findByStoreIdAndId(paid.getStoreId(),paid.getOrderId());
            order.setStatus(OrderStatus.cooking);

            orderRepository.save(order);
            System.out.println("##### listener order paid : " + paid.toJson());
        }
    }

    // 계산 취소되어 주문 취소됨
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRefunded(@Payload Refunded refunded){

         if(refunded.isMe()){
            Order order = orderRepository.findByStoreIdAndId(refunded.getStoreId(),refunded.getOrderId());
            order.setStatus(OrderStatus.canceled);

            orderRepository.save(order);
            System.out.println("##### listener order refunded : " + refunded.toJson());
        }
    }

    // 조리실에서 조리 취소됨
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCanceled(@Payload OrderCancelled orderCancelled){

         if(orderCancelled.isMe()){
            Order order = orderRepository.findByStoreIdAndId(orderCancelled.getStoreId(), orderCancelled.getId());
            order.setStatus(OrderStatus.canceled);

            orderRepository.save(order);
            System.out.println("##### listener order canceled : " + orderCancelled.toJson());
        }
    }


}
