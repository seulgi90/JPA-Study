package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
  
  public static void main(String[] args) {
    
//    AppConfig appConfig = new AppConfig();
//    MemberService memberService = appConfig.memberService();
//    MemberService memberService = new MemberServiceImpl();
  
    // 스프링 컨테이너 생성
    // ApplicationContext = 스프링 컨테이너라 한다 , 인터페이스 이다
    // AppConfig.class 환경설정 정보를 가지고 @Bean이라 적힌 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록한다
    // (AppConfig.class); : 구성 정보로 지정
    // 스프링 컨테이너는 파라미터로 넘어온 설정 클래스 정보를 사용해서 스프링 빈을 등록한다
    // new AnnotationConfigApplicationContext(AppConfig.class) : ApplicationContext 인터페이스의 구현체
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    // @Bean memberService() 메서드 이름으로 등록이 된다 : "memberService"
    // MemberService.class :  반환 타입
    // 스프링 빈은 applicationContext.getBean() 메서드를 사용해서 찾을 수 있다
    MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
  
    Member member = new Member(1L, "memberA", Grade.VIP);
    memberService.join(member);
  
    Member findMember = memberService.findMember(1L);
    System.out.println("find Member=" + findMember.getName());
    System.out.println("new member = " + member.getName());
    
  }
}
