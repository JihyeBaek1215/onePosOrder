package onepos.domain;

import javax.persistence.*;

@Entity
@Table(name="Order_table")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;

    private int storeId;
    private int tableNo;
    private String holeflag; // "Pack" "Hole"
    private OrderStatus status;

    @Embedded
    OrderItem orderItems = new OrderItem();

    // 주문했을 때
    @PostPersist
    public void onPostPersist(){
        if(this.getStatus() == OrderStatus.orderRequest){
            Ordered ordered = new Ordered();
            ordered.setId(this.getId());
            ordered.setStoreId(this.getStoreId());
            ordered.setOrderItems(this.getOrderItems());
            ordered.setStatus(this.getStatus());
            ordered.setHoleflag(this.getHoleflag());
            ordered.setTableNo(this.getTableNo());
            ordered.publishAfterCommit(); // kafka 발행
        }
    }

    //손님이 주문취소했을 때
    @PostUpdate
    public void onPostUpdate(){
        if(this.getStatus() == OrderStatus.cancelRequest){
            OrderCancelled ordercancelled = new OrderCancelled();
            ordercancelled.setId(this.getId());
            ordercancelled.setStatus(this.getStatus());
            ordercancelled.publishAfterCommit();
        }
    }

    public int getId(){
        return id;
    }
    public OrderItem getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(OrderItem orderItems) {
        this.orderItems = orderItems;
    }
    public int getTableNo() {
        return tableNo;
    }
    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }
    public int getStoreId() {
        return storeId;
    }
    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getHoleflag() {
        return holeflag;
    }
    public void setHoleflag(String holeflag) {
        this.holeflag = holeflag;
    }

}