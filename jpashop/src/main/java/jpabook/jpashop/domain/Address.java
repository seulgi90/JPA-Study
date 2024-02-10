package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address { // 값 타입은 변경이 불가능하게 설계해야한다

  private String city;
  private String street;
  private String zipcode;
  
  // JPA 스펙상 엔티티나 임베디드 타입( `@Embeddable` )은
  // 자바 기본 생성자(default constructor)를 `public` 또는 `protected` 로 설정해야 한다
  // JPA 구현 라이브러리가 객체를 생성할 때 리플렉션 같은 기술을 사용 할수 있도록 지원해야하기 때문
  public Address() {
  }
  
  public Address(String city, String street, String zipcode) { // 생성 할때만 셋팅이되도록,
    this.city = city;
    this.street = street;
    this.zipcode = zipcode;
  }
}
