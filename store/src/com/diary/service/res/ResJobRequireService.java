package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResJobRequire;
import com.diary.providers.store.res.ResJobRequireStore;
import com.google.inject.Singleton;
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
public class ResJobRequireService extends HQuery implements ResJobRequireStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResJobRequire getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResJobRequire.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResJobRequire> getListByJobId(Long jobId) throws StoreException {
        return $($alias("jobId", "jobId"), $eq("jobId.id", jobId)).list(ResJobRequire.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResJobRequire> getList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResJobRequire.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResJobRequire resJobRequire, Persistent persistent) throws StoreException {
        $(resJobRequire).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResJobRequire resJobRequire) throws StoreException {
        $(resJobRequire).delete();
    }
}
