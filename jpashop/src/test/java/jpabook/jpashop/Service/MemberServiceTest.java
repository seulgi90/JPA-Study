package jpabook.jpashop.Service;


import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {
  
  @Autowired MemberService memberService;
  @Autowired MemberRepository memberRepository;
  @Autowired EntityManager em;
  
  @Test
  @Rollback(value = false)
  public void 회원가입() throws Exception {
    // given
    Member member = new Member();
    member.setName("kim");
  
    // when
    Long saveId = memberService.join(member);
  
    // then
    em.flush(); // @Transactional이 걸려있기때문에 insert를 하지 않음 -> 그래도 확인해보고싶어서,
    Assertions.assertEquals(member, memberRepository.findOne(saveId));
  }
  
  @Test
  public void 중복_회원_예외() throws Exception {
    // given
    Member member1 = new Member();
    member1.setName("kim");
    
    Member member2 = new Member();
    member2.setName("kim");
    
    // when
    memberService.join(member1);
    // jUnit5 exception 처리방법
    Assertions.assertThrows(IllegalStateException.class, () -> {
          memberService.join(member2);
        }); // 예외 발생해야 함
    
//    try {
//      memberService.join(member2); // 예외 발생해야 함
//    } catch (IllegalStateException e) {
//      return;
//    } -> @Test(expected = IllegalStateException.class) // jUnit4 지원
    
    // then
    // 위에서 예외 발생 여부를 검증하므로 삭제
//    Assertions.fail("예외가 발생해야 한다.");
  }
}