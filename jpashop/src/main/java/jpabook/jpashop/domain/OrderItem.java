package jpabook.jpashop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jpabook.jpashop.domain.item.Item;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {

//        OrderItem orderItem = OrderItem.builder()
//                .item(item)
//                .orderPrice(orderPrice) // 할인 정책 등을 고려하여 나누는 것이 맞다
//                .count(count)
//                .build();

        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); // 주문 된 수량 만큼 재고 감소

      return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel() {
      getItem().addStock(count); // 재고 수량 원복
    }

    //==조회 로직==//
    /**
     * 주문상품 전체 가격 조회
     *
     */
    public int getTotalPrice() {
      return getOrderPrice() * getCount();
    }
}
