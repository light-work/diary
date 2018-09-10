package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEventNoResult;
import com.diary.providers.store.res.ResEventNoResultStore;
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
public class ResEventNoResultService extends HQuery implements ResEventNoResultStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResEventNoResult getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResEventNoResult.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResEventNoResult> getByNoId(Long noId) throws StoreException {
        return $($eq("noId.id", noId)).list(ResEventNoResult.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResEventNoResult> getByList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResEventNoResult.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResEventNoResult resEventNoResult, Persistent persistent) throws StoreException {
        $(resEventNoResult).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResEventNoResult resEventNoResult) throws StoreException {
        $(resEventNoResult).delete();
    }
}
