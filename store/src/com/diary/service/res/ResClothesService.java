package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResClothes;
import com.diary.providers.store.res.ResClothesStore;
import com.google.inject.Singleton;
import org.guiceside.commons.Page;
import org.guiceside.persistence.TransactionType;
import org.guiceside.persistence.Transactional;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.HQuery;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;

/**
 * Created by Lara Croft on 2016/12/21.
 */
@Singleton
public class ResClothesService extends HQuery implements ResClothesStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)

    public ResClothes getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResClothes.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<ResClothes> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(ResClothes.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResClothes resClothes, Persistent persistent) throws StoreException {
        $(resClothes).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResClothes resClothes) throws StoreException {
        $(resClothes).delete();
    }
}
