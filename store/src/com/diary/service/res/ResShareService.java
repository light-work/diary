package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResShare;
import com.diary.providers.store.res.ResShareStore;
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
public class ResShareService extends HQuery implements ResShareStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResShare getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResShare.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResShare getByGenderId(Integer gender) throws StoreException {
        return $($eq("gender",gender)).get(ResShare.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<ResShare> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(ResShare.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResShare> getList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResShare.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResShare resShare, Persistent persistent) throws StoreException {
        $(resShare).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResShare resShare) throws StoreException {
        $(resShare).delete();
    }
}
