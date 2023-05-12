package com.kreamish.kream.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Category extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long categoryId;

    @Length(min=1,max=200)
    @Column(name = "name",nullable = false, unique = true)
    @Setter
    private String name;

    public static Category of(String name) {
        return new Category(null, name);
    }
}
