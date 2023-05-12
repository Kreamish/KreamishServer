package com.kreamish.kream.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Item extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;

    public static Item of(String name, String subName, String modelCode, LocalDate releaseDate, Long releasePrice, String representativeColor, Brand brand, Category category, CategoryDetail categoryDetail) {
        return new Item(null, name, subName, modelCode, releaseDate, releasePrice, representativeColor, brand, category, categoryDetail);
    }

    public static Item of(String name, String subName, Brand brand, Category category, CategoryDetail categoryDetail) {
        return new Item(null, name, subName, null,null, null, null, brand, category, categoryDetail);
    }

    @Length(min=1,max=200)
    @Column(name = "name",nullable = false, unique = true)
    @Setter
    private String name;

    @Length(min=1,max=200)
    @Column(name = "sub_name",nullable = false, unique = true)
    @Setter
    private String subName;

    @Length(min=1,max=50)
    @Column(name = "model_dode")
    @Setter
    private String modelCode;

    @Column(name = "release_date")
    @Setter
    private LocalDate releaseDate;

    @Max(Long.MAX_VALUE)
    @Min(0L)
    @Column(name = "release_price")
    @Setter
    private Long releasePrice;

    @Length(min=1,max=50)
    @Column(name = "representative_color")
    @Setter
    private String representativeColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brandId",nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId",nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryDetailId",nullable = false)
    private CategoryDetail categoryDetail;
}
