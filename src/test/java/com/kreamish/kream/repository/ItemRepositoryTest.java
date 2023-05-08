package com.kreamish.kream.repository;

import com.kreamish.kream.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void simpleDbTest() {
        //given
        String itemName = "item1";
        Item item = new Item();
        item.setName(itemName);

        em.persist(item);
        em.flush();
        em.clear();

        //when
        Item findItem = em.find(Item.class, item.getItemId());

        //then
        assertThat(findItem).isNotNull();
        assertThat(findItem.getName()).isEqualTo(itemName);
    }

    @Test
    void simpleSpringDataJpaTest() {
        //given
        String itemName = "item1";
        Item item = new Item();
        item.setName(itemName);

        itemRepository.save(item);

        //when
        Item findItem = itemRepository.findById(item.getItemId())
                .orElseThrow();

        //then
        assertThat(findItem.getName()).isEqualTo(itemName);
    }
}