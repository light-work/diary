package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResPlan;
import com.diary.providers.store.res.ResPlanStore;
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
public class ResPlanService extends HQuery implements ResPlanStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResPlan getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResPlan.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Integer getMaxOrder() throws StoreException {
        return $($max("displayOrder")).value(ResPlan.class,Integer.class);
    }


    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<ResPlan> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(ResPlan.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResPlan resPlan, Persistent persistent) throws StoreException {
        $(resPlan).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResPlan resPlan) throws StoreException {
        $(resPlan).delete();
    }
}
