//package jpabook.jpashop;
//
//import jpabook.jpashop.domain.Member;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//class MemberRepositoryTest {
//
//  @Autowired MemberRepository memberRepository;
//
//  @Test
//  @Transactional // entity data의 모든 변경은 Transactional안에서 이루어져야한다
//  @Rollback(value = false)
//  public void testMember() throws Exception {
//    //given
//    Member member = new Member();
//    member.setUsername("memberA");
//
//    //when
//    Long saveId = memberRepository.save(member);
//    Member findMember = memberRepository.find(saveId);
//
//    //then
//    Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//    Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//    Assertions.assertThat(findMember).isEqualTo(member); // 같은 영속성컨텍스트 사용 : 식별자가 같으면 같은 엔티티로 식별
//  }
//}