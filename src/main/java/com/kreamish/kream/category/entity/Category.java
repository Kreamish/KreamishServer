package com.kreamish.kream.category.entity;

import com.kreamish.kream.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Length(min = 1, max = 200)
    @Column(name = "name", nullable = false, unique = true)
    @Setter
    private String name;

    public static Category of(String name) {
        return new Category(null, name);
    }
}
