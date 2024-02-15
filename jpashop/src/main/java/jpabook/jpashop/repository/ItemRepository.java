package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;
    
    // 변경 감지(Dirty Checking)와 병합(merge)
    // 준영속 엔티티 : 영속성 컨텍스트가 더는 관리하지 않는 엔티티
    // itemService.saveItem(book); -> 수정을 시도하는 Book객체
    // Book객체는 이미 DB에 한번 저장되어 식별자가 존재
    // 이렇게 임의로 만들어낸 엔티티도 기존 식별자를 가지고 있으면 준영속 엔티티로 볼 수 있다
    
    // 준영속 엔티티를 수정하는 2가지 방법 : 변경 감지(Dirty Checking) 기능 사용 / 병합(merge) 사용
    // 변경 감지 기능을 사용하면 원하는 속성만 선택해서 변경 가능
    // 병합을 사용하면 모든 속이 변경 됨 -> 병합 시 값이 없으면 null로 업데이트 할 위험도 있음

    public void save(Item item) {
        if (item.getId() == null) { // id값이 없다면 신규
            em.persist(item);
        } else {
            em.merge(item);
            // 병합(merge) : 준영속 상태의 엔티티를 영속 상태로 변경할 때 사용하는 기능
            // 파라미터로 넘어온 준영속 엔티티의 식별자 값으로 1차 캐시에서 엔티티를 조회
            // 만약 1차 캐시에 엔티티가 없으면 DB에서 엔티티를 조회하고, 1차 캐시에 저장
            // 조회한 영속 엔티티 mergeItem에 item 엔티티의 값을 채워 넣는다
            // item의 '모든 값'을 mergeItem에 밀어 넣는다(병합은 모든 필드를 교체)
            // 예) mergeItem의 이름인 Test1이라는 이름이 수정하는 내용인 test수정으로 변경 됨
            // 영속 상태인 mergeItem을 반환 <-> item과는 다름 유의!
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
