package jpabook.jpashop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {
  
    @Id @GeneratedValue
    @Column(name = "memeber_id")
    private Long id;

    private  String name;

    @Embedded // 내장타입
    private Address address;

    // 컬렉션은 필드에서 바로 초기화하는 것이 안전하다 -> null의 문제에서
    // 하이버네이트는 엔티티를 영속화 할 떄, 컬렉션을 감싸서 하이버네이트가 제공하는 내장 컬렉션으로 변경한다
    @OneToMany(mappedBy = "member") // mappedBy : 해당 필드의 소유자가 누구인지 알려주기 위한 파라미터
    private List<Order> orders = new ArrayList<>();
  
}
