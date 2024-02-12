package jpabook.jpashop.Service;


import java.util.List;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
  
  private final MemberRepository memberRepository;
  
  /**
   * 회원 가입
   */
  @Transactional
  public Long join(Member member) {
    validateDuplicateMember(member); // 중복 회원 검증
    memberRepository.save(member);
    return member.getId();
  }
  
  private void validateDuplicateMember(Member member) {
    List<Member> findMembers = memberRepository.findByName(member.getName());
    if (!findMembers.isEmpty()) {
      throw new IllegalStateException("이미 존재하는 회원입니다");
    }
    // EXCEPTION
  }
  
  // 회원 전체 조회
  @Transactional(readOnly = true) // readOnly = true(읽기전용)가 있을경우 jpa가 성능을 최적해준다
  public List<Member> findMembers() {
    return memberRepository.findAll();
  }
  
  // 회원 단건 조회
  @Transactional(readOnly = true)
  public Member findMember(Long memberId) {
    return memberRepository.findOne(memberId);
  }
}
