package jpabook.jpashop.Service;

import jpabook.jpashop.domain.item.Book;
import org.springframework.transaction.annotation.Transactional;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(Item item) {
        itemRepository.save(item);
    }
    
    // 변경 감지(Dirty Checking)에 의해서 데이터를 변경
    public void updateItem(Long itemId, UpdateItemDto updateItemDto) {
       
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(updateItemDto.getName());
        findItem.setPrice(updateItemDto.getPrice());
        findItem.setStockQuantity(updateItemDto.getStockQuantity());
        
        // 병합 기능 사용을 하게되면 위 코드를 em.merge(item) 한줄로 쓸 수는 있으나
        // 변경 감지를 통한 데이터 변경 권장
    }

    @Transactional(readOnly = true)
    public List<Item> findItems() {
       return itemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }
}
