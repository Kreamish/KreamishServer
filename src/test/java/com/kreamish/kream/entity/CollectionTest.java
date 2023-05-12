package com.kreamish.kream.entity;

import com.kreamish.kream.repository.CollectionRepository;
import com.mysema.commons.lang.Assert;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CollectionTest {

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private JPAQueryFactory queryFactory;
    @Test
    @DisplayName("성공: create")
    void success_create() {
        //given
        Collection collection = new Collection();
        collection.setName("123");

        //when
        Collection saved = collectionRepository.save(collection);

        //then
        Assertions.assertThat(saved).isNotNull();

    }

}