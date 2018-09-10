package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResCouple;
import com.diary.providers.store.res.ResCoupleStore;
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
public class ResCoupleService extends HQuery implements ResCoupleStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)

    public ResCouple getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResCouple.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<ResCouple> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(ResCouple.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResCouple resCouple, Persistent persistent) throws StoreException {
        $(resCouple).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResCouple resCouple) throws StoreException {
        $(resCouple).delete();
    }
}
