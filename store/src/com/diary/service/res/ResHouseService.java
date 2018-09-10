package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResHouse;
import com.diary.providers.store.res.ResHouseStore;
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
public class ResHouseService extends HQuery implements ResHouseStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)

    public ResHouse getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResHouse.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<ResHouse> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(ResHouse.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResHouse resHouse, Persistent persistent) throws StoreException {
        $(resHouse).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResHouse resHouse) throws StoreException {
        $(resHouse).delete();
    }
}
