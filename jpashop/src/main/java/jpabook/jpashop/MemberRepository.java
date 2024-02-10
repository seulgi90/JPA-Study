package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

  @PersistenceContext // 이 어노테이션을 사용하면 스프링부트가 알아서 EntityManager를 주입해준다
  private EntityManager em;
  
  public Long save(Member member) { // 커맨드와 쿼리를 분리하기위해,
    em.persist(member);
    return member.getId();
  }
  
  public Member find(Long id) {
    return em.find(Member.class, id);
  }
  
}
