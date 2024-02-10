package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService {
 
 // private final MemberRepository memberRepository = new MemoryMemberRepository();
 
 // 주문 서비스 클라이언트가 인터페이스인  DiscountPolicy 뿐만 아니라 구체 클래스인 FixDiscountPolicy도 함꼐 의존 : DIP 위반
 // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
 // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
  
  // 인터페이스에만 의존하도록 코드 변경 : DIP 준수
  private final MemberRepository memberRepository;
  private final DiscountPolicy discountPolicy;
  
  // OrderServiceImpl은 필요한 인터페이스들을 호출하지만 어떤 구현 객체들이 실행 될지 모른다
  // 동적인 객체 인스턴스 의존 관계 : 실행 시점에 생성된 객체 인스턴스의 참조가 연결된 의존 관계
  // 실행 시점에 외부에서 실제 구현 객체를 생성하고 클라이언트에 전달해서 클라이언트와 서버의 실제 의존 관계가 연결되는 것을 의존관계 주입이라 한다
  public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  }
  
  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    Member member = memberRepository.findById(memberId);
    int discountPrice = discountPolicy.discount(member, itemPrice);
    
    return new Order(memberId, itemName, itemPrice, discountPrice);
  }
}
