package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResLuck;
import com.diary.providers.store.res.ResLuckStore;
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
public class ResLuckService extends HQuery implements ResLuckStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)

    public ResLuck getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResLuck.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<ResLuck> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(ResLuck.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResLuck> getList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResLuck.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResLuck resLuck, Persistent persistent) throws StoreException {
        $(resLuck).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResLuck resLuck) throws StoreException {
        $(resLuck).delete();
    }
}
