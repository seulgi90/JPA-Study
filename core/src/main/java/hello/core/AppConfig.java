package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration 이 붙은 AppConfig를 설정(구성) 정보로 사용
@Configuration
// 애플리케이션의 실제 동작에 필요한 구현 객체를 생성한다
// 객체 인스턴스를 생성하고 , 그 참조값을 전달해서 연결된다
public class AppConfig {
  
  //  이렇게 스프링 컨테이너에 등록된 객체를 스프링 빈이라 한다
  @Bean
  // 빈 이름: memberService() 메서드 이름을 사용
  // 빈 객체: MemberServiceImpl <= return  new MemberServiceImpl
  public MemberService memberService() {
  
    // 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해서 주입(연결)해준다
    // MemoryMemberRepository 객체를 생성하고 그 참조값을 MemberServiceImpl을 생성하면서 생성자로 전달
    // MemberServiceImpl 입장에서 보면 의존관계를 마치 외부에서 주입해주는 것 같다고 해서 DI 의존관계 주입 또는 의존성 주입이라 한다
    // return  new MemberServiceImpl(new MemoryMemberRepository());
    return  new MemberServiceImpl(memberRepository());
  }
  
  @Bean
  public MemberRepository memberRepository() {
    
    return new MemoryMemberRepository();
  }
  
  @Bean
  // 애플리케이션의 실제 동작에 필요한 구현 객체를 생성한다
  public OrderService orderService() {
   // return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy);
    return new OrderServiceImpl(memberRepository(), discountPolicy());
  }
  
  @Bean
  public DiscountPolicy discountPolicy() {
    // 할인 정책을 변경해도 구성 영역만 변경하면됨 : 주문 서비스 코드 변경 필요 없음
    // AppConfig가 FixDiscountPolicy 객체 인스턴스를 클라이언트 코드 대신 생성해서 클라이언트 코드에 의존관계를 주입
    // return new FixDiscountPolicy();
    return new RateDiscountPolicy();
    
  }
}
