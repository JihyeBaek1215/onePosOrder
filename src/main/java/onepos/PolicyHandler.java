package onepos;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import onepos.config.kafka.KafkaProcessor;
import onepos.domain.Canceled;
import onepos.domain.Cooking;
import onepos.domain.Order;
import onepos.domain.OrderCancelled;
import onepos.domain.OrderRepository;
import onepos.domain.OrderStatus;
import onepos.domain.Paid;
import onepos.domain.Refunded;
import onepos.domain.Served;

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

    
    // 계산 취소되어 주문 취소됨
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRefunded(@Payload Refunded refunded){

         if(refunded.isMe()){
            Order order = orderRepository.findById(refunded.getOrderId());
            order.setStatus(OrderStatus.canceled);

            orderRepository.save(order);
            System.out.println("##### listener order refunded : " + refunded.toJson());
        }
    }

    // 조리실 주문 취소됨
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCanceled(@Payload Canceled canceled){

        if(canceled.isMe()){
            Order order = orderRepository.findById(canceled.getOrderId());
            order.setStatus(OrderStatus.canceled);

            orderRepository.save(order);
            System.out.println("##### listener cook canceled: " + canceled.toJson());
        }
    }


    // 조리실에서 주문승인
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCooking(@Payload Cooking cooking){

         if(cooking.isMe()){
            Order order = orderRepository.findById(cooking.getOrderId());
            order.setStatus(OrderStatus.cooking);

            orderRepository.save(order);
            System.out.println("##### listener cook start: " + cooking.toJson());
        }
    }


    // 조리실 주문 완료됨
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverServed(@Payload Served served){

        if(served.isMe()){
            Order order = orderRepository.findById(served.getOrderId());
            order.setStatus(OrderStatus.served);

            orderRepository.save(order);
            System.out.println("##### listener served: " + served.toJson());
        }
    }

}
