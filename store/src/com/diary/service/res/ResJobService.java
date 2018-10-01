package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResJob;
import com.diary.providers.store.res.ResJobStore;
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
public class ResJobService extends HQuery implements ResJobStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)

    public ResJob getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResJob.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<ResJob> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(ResJob.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResJob> getList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResJob.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResJob resJob, Persistent persistent) throws StoreException {
        $(resJob).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResJob resJob) throws StoreException {
        $(resJob).delete();
    }
}
