package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResCar;
import com.diary.providers.store.res.ResCarStore;
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
public class ResCarService extends HQuery implements ResCarStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResCar getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResCar.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResCar getByTitle(String title) throws StoreException {
        return $($eq("title",title)).get(ResCar.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<ResCar> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(ResCar.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResCar> getList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResCar.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResCar resCar, Persistent persistent) throws StoreException {
        $(resCar).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResCar resCar) throws StoreException {
        $(resCar).delete();
    }
}
