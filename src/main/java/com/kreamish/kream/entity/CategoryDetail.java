package com.kreamish.kream.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class CategoryDetail extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryDetailId;

    @Length(min=1,max=200)
    @Column(name = "name", nullable = false, unique = true)
    @Setter
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    public static CategoryDetail of(Category category, String name) {
        return new CategoryDetail(null, name, category);
    }
}
