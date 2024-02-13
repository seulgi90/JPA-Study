package jpabook.jpashop.domain.item;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

import jpabook.jpashop.Exception.NotEnoughStockException;
import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {
  
  @Id @GeneratedValue
  @Column(name = "item_id")
  private Long id;

  private String name;
  
  private int price;
  
  private int stockQuantity; // 재고수량
  
  @ManyToMany(mappedBy = "items")
  private List<Category> categories = new ArrayList<>();

  //==비즈니스 로직==/
  // stockQuantity 를 변경 할려면 Setter를 사용해서 외부에서 값을 만드는 것이 아닌 이 안에서 비즈니스 로직을 가지고 아래처럼 변경하자(객체지향적)
  /**
   * stock 증가
   * @param quantity
   */
  public void addStock(int quantity) {
    this.stockQuantity += quantity;
  }

  /**
   * stock 감소
   * @param quantity
   */
  public void removeStock(int quantity) {
    int restStock = this.stockQuantity - quantity;
    if (restStock < 0) {
      throw new NotEnoughStockException("need more stock");
    }
    this.stockQuantity = restStock;
  }
}
