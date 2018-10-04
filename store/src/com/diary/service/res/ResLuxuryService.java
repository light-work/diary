package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResLuxury;
import com.diary.providers.store.res.ResLuxuryStore;
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
public class ResLuxuryService extends HQuery implements ResLuxuryStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)

    public ResLuxury getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResLuxury.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<ResLuxury> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(ResLuxury.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResLuxury> getList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResLuxury.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResLuxury resLuxury, Persistent persistent) throws StoreException {
        $(resLuxury).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResLuxury resLuxury) throws StoreException {
        $(resLuxury).delete();
    }
}
