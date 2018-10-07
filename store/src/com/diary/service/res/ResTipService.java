package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResTip;
import com.diary.providers.store.res.ResTipStore;
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
public class ResTipService extends HQuery implements ResTipStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)

    public ResTip getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResTip.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<ResTip> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(ResTip.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResTip> getList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResTip.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResTip resTip, Persistent persistent) throws StoreException {
        $(resTip).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResTip resTip) throws StoreException {
        $(resTip).delete();
    }
}
