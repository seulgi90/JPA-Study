package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest4 {
  @Autowired
  MemberRepository memberRepository;
  
  @Test
  @Transactional // entity data의 모든 변경은 Transactional안에서 이루어져야한다
  public void testMember() throws Exception {
    //given
    Member member = new Member();
    member.setUsername("memberA");
    
    //when
    Long saveId = memberRepository.save(member);
    Member findMember = memberRepository.find(saveId);
    
    //then
    Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
    Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
  }
}