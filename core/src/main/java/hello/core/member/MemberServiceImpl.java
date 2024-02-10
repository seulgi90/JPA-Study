package hello.core.member;

public class MemberServiceImpl implements MemberService {
  
  // MemberRepository 인터페이스만 의존한다 => 구체 클래스를 몰라도 된다
  private final MemberRepository memberRepository;
  
  // 생성자를 통해 어떤 구현 객체가 들어올지(주입될지) 알 수 없다
  // 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부(AppConfig)에서 결정
  public MemberServiceImpl(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }
  
  @Override
  public void join(Member member) {
    memberRepository.save(member);
  }
  
  @Override
  public Member findMember(Long memberId) {
    return memberRepository.findById(memberId);
  }
}
