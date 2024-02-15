package jpabook.jpashop.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class UpdateItemDto {
    
    String name;
    int price;
    int stockQuantity;
}
