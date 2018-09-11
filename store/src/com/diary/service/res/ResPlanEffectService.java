package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResPlanEffect;
import com.diary.providers.store.res.ResPlanEffectStore;
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
public class ResPlanEffectService extends HQuery implements ResPlanEffectStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResPlanEffect getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResPlanEffect.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResPlanEffect> getListByPlanId(Long planId) throws StoreException {
        return $($alias("planId", "planId"), $eq("planId.id", planId)).list(ResPlanEffect.class);
    }


    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResPlanEffect> getList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResPlanEffect.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResPlanEffect resPlanEffect, Persistent persistent) throws StoreException {
        $(resPlanEffect).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResPlanEffect resPlanEffect) throws StoreException {
        $(resPlanEffect).delete();
    }
}
