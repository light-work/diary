package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResFund;
import com.diary.providers.store.res.ResFundStore;
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
public class ResFundService extends HQuery implements ResFundStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)

    public ResFund getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResFund.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<ResFund> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(ResFund.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResFund> getList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResFund.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResFund resFund, Persistent persistent) throws StoreException {
        $(resFund).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResFund resFund) throws StoreException {
        $(resFund).delete();
    }
}
