package com.kreamish.kream.legacy.entity;

import com.kreamish.kream.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Table(name = "collection")
public class Collection extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collection_id")
    private Long collectionId;

    @Length(min = 1, max = 50)
    @Column(name = "name", nullable = false, unique = true)
    @Setter
    private String name;

    public static Collection of(String name) {
        return new Collection(null, name);
    }
}
