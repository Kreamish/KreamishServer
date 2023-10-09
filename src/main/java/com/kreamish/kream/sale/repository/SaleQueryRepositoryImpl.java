package com.kreamish.kream.sale.repository;

import com.kreamish.kream.sale.entity.Sale;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.Optional;

public class SaleQueryRepositoryImpl implements SaleQueryRepository {

    @PersistenceContext
    EntityManager entityManager;

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
