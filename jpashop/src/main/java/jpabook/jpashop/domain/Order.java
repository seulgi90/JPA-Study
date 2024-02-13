package jpabook.jpashop.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Entity
@Table(name="orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
  
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 회원 - 주문 1:n 관계, 양방향
    @JoinColumn(name = "member_Id") // FK, 외래 키가 있는 곳을 연관관계의 주인으로 정해라
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // order를 persist하면 들어와있는 orderItems도 강제로 persist 해준다 -> CascadeType.ALL
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // order를 persist하면 들어와있는 delivery도 강제로 persist 해준다 -> CascadeType.ALL
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    // ==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) { // ... 가변인자 : 내부적으로 배열을 생성, 가변인자는 마지막에 사용
// @NoArgsConstructor(access = AccessLevel.PROTECTED) 사용으로 빌더 사용 불가
// 같은 패키지 내 클래스만 해당 생성자에 접근할 수 있기때문 (외부에서 new, builder() 불가)
        //        Order order = Order.builder()
//                .member(member)
//                .delivery(delivery)
//                .build();
        
        
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        
        for (OrderItem orderItem : orderItems) {
          order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // 도메인 모델 패턴 : 엔티티가 비즈니스 로직을 가지고 객체지향의 특성을 적극 활용 하는 것 
    // <-> 트랜잭션 스크립트 패턴 : 엔티티에는 비즈니스 로직이 거의 없고 서비스 계층에서 대부분의 비즈니스 로직을 처리하는 것
    //==비지니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
          throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
          orderItem.cancel();
        }
    }

    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum(); // 람다식
  //    int totalPrice = 0;
  //    for(OrderItem orderItem : orderItems) {
  ////      totalPrice += orderItem.getOrderPrice(); // 안되는 이유? 주문 가격과 주문수량을 곱해서 산출 하기때문에 계산 로직 필요
  //      totalPrice += orderItem.getTotalPrice();
  //    }
  //    return totalPrice;

  }
}
