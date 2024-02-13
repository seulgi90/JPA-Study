package jpabook.jpashop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Category {
  
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    // @ManyToMany` 는 편리한 것 같지만, 중간 테이블( `CATEGORY_ITEM` )에 컬럼을 추가할 수 없고,
    // 세밀하게 쿼리를 실행하기 어렵기 때문에 실무에서 사용하기에는 한계가 있다 ->  실무에서는 사용하지 말자
    // 모든 연관관계는 지연설정(fetch = FetchType.LAZY)으로 설정하자!! : OneToMany는 default가 LAZY라 제외
    // -> 연관된 엔티티를 함께 DB에서 조회해야한다면 fetch join 또는 엔티티 그래프 기능을 사용한다
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "category_item",
          joinColumns = @JoinColumn(name = "category_id"),
          inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //==연관계 메서드==//
    public void addChildCategory(Category child) {
        this.child.add(child); // 컬렉션에도 넣고,
        child.setParent(this); // 자식에도 부모가 누구인지 넣어줘야한다
    }
}
