package onepos.domain;
import java.util.*;

import onepos.AbstractEvent;

public class Ordered extends AbstractEvent {

    private int id; // 주문ID
    private int storeId; // 매장ID
    private int tableNo; // 테이블번호
    private String holeflag; // 포장여부 "Pack" 포장 "Hole" 홀
    private OrderStatus status;
    OrderItem orderItems = new OrderItem();

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public OrderItem getOrderItems(){
        return orderItems;
    }
    public void setOrderItems(OrderItem orderItems){
        this.orderItems= orderItems;
    }
    public int getStoreId() {
        return storeId;
    }
    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
    public String getHoleflag() {
        return holeflag;
    }
    public void setHoleflag(String holeflag) {
        this.holeflag = holeflag;
    }
    public int getTableNo() {
        return tableNo;
    }
    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

}