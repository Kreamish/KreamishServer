package com.kreamish.kream.item.entity;

import com.kreamish.kream.category.entity.Category;
import com.kreamish.kream.categorydetail.entity.CategoryDetail;
import com.kreamish.kream.common.entity.BaseEntity;
import com.kreamish.kream.legacy.entity.Brand;
import com.kreamish.kream.legacy.entity.ItemCollectionRel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Table(name = "item")
@Builder
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @OneToMany(mappedBy = "item")
    private List<ItemSizes> itemSizes;

    @OneToMany(mappedBy = "item")
    private List<ItemCollectionRel> itemCollectionRelList;

    @Length(min = 1, max = 200)
    @Column(name = "name", nullable = false, unique = true)
    @Setter
    private String name;

    @Length(min = 1, max = 200)
    @Column(name = "sub_name", nullable = false, unique = true)
    @Setter
    private String subName;

    @Length(min = 1, max = 200)
    @Column(name = "image_url", unique = true)
    @URL
    @Setter
    private String imageUrl;

    @Length(min = 1, max = 50)
    @Column(name = "model_code")
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

    @Length(min = 1, max = 50)
    @Column(name = "representative_color")
    @Setter
    private String representativeColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_detail_id", nullable = false)
    private CategoryDetail categoryDetail;

    public static Item of(String name, String subName, String modelCode, LocalDate releaseDate,
        Long releasePrice, String representativeColor, Brand brand, Category category,
        CategoryDetail categoryDetail, String imageUrl) {
        return new Item(null, null, null, name, subName, imageUrl, modelCode, releaseDate, releasePrice,
            representativeColor,
            brand, category, categoryDetail);
    }

    public static Item of(String name, String subName, Brand brand, Category category,
        CategoryDetail categoryDetail, String imageUrl) {
        return new Item(null, null, null, name, subName, imageUrl, null, null, null, null, brand, category,
            categoryDetail);
    }
}
