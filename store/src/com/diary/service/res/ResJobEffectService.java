package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResJobEffect;
import com.diary.providers.store.res.ResJobEffectStore;
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
public class ResJobEffectService extends HQuery implements ResJobEffectStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResJobEffect getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResJobEffect.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResJobEffect> getListByJobId(Long jobId) throws StoreException {
        return $($alias("jobId", "jobId"), $eq("jobId.id", jobId)).list(ResJobEffect.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResJobEffect> getList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResJobEffect.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResJobEffect resJobEffect, Persistent persistent) throws StoreException {
        $(resJobEffect).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResJobEffect resJobEffect) throws StoreException {
        $(resJobEffect).delete();
    }
}
