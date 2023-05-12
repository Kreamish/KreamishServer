package com.kreamish.kream.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
public class Item extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;

    @Column(name = "name",nullable = false)
    @Setter
    private String name;

    @Column(name = "sub_name",nullable = false)
    @Setter
    private String subName;

    @Column(name = "model_dode")
    @Setter
    private String modelCode;

    @Column(name = "release_date")
    @Setter
    private LocalDate releaseDate;

    @Column(name = "release_price")
    @Setter
    private String releasePrice;

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
    @JoinColumn(name = "categoryDetailId")
    private CategoryDetail categoryDetail;
}
