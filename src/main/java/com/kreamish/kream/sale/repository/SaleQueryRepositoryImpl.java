package com.kreamish.kream.sale.repository;

import static com.kreamish.kream.common.entity.DealStatus.PENDING;
import static com.kreamish.kream.item.entity.QItem.item;
import static com.kreamish.kream.itemsizes.entity.QItemSizes.itemSizes;
import static com.kreamish.kream.sale.entity.QSale.sale;

import com.kreamish.kream.sale.dto.PendingSaleDto;
import com.kreamish.kream.sale.entity.Sale;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SaleQueryRepositoryImpl implements SaleQueryRepository {

    private final JPAQueryFactory query;
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<PendingSaleDto> findSaleByDealStatusAndItemId(Long itemId) {
        return query
            .select(
                Projections.bean(
                    PendingSaleDto.class,
                    itemSizes.size.as("itemSizes"),
                    sale.salePrice.as("price"),
                    sale.salePrice.count().as("quantity")
                )
            )
            .from(sale)
            .join(sale.itemSizes, itemSizes)
            .join(itemSizes.item, item)
            .where(
                sale.saleStatus.eq(PENDING),
                item.itemId.eq(itemId))
            .groupBy(sale.itemSizes, sale.salePrice)
            .fetch();
    }

    @Override
    public List<PendingSaleDto> findSaleByDealStatusAndItemSizesId(Long itemSizesId) {
        return query
            .select(
                Projections.bean(
                    PendingSaleDto.class,
                    itemSizes.size.as("itemSizes"),
                    sale.salePrice.as("price"),
                    sale.salePrice.count().as("quantity")
                )
            )
            .from(sale)
            .where(
                sale.saleStatus.eq(PENDING),
                sale.itemSizes.itemSizesId.eq(itemSizesId))
            .groupBy(sale.itemSizes, sale.salePrice)
            .fetch();
    }

    @Override
    public Optional<Sale> findTheLowestPriceByItemId(Long itemId) {
        TypedQuery<Sale> query = entityManager.createQuery("select s from Sale s "
            + "where s.itemSizes.item.itemId = :itemId and s.saleStatus = 'PENDING' "
            + "order by s.salePrice asc, s.saleId desc", Sale.class);

        try {
            return Optional.ofNullable(query.setFirstResult(0)
                .setMaxResults(1)
                .setParameter("itemId", itemId)
                .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Sale> findTheLowestPriceByItemSizesId(Long itemSizesId) {
        TypedQuery<Sale> query = entityManager.createQuery("select s from Sale s "
            + "where s.itemSizes.itemSizesId = :itemSizesId and s.saleStatus = 'PENDING' "
            + "order by s.salePrice asc, s.saleId desc ", Sale.class);

        try {
            return Optional.ofNullable(query.setFirstResult(0)
                .setMaxResults(1)
                .setParameter("itemSizesId", itemSizesId)
                .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
