package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEventResultEffect;
import com.diary.providers.store.res.ResEventResultEffectStore;
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
public class ResEventResultEffectService extends HQuery implements ResEventResultEffectStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResEventResultEffect getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResEventResultEffect.class);
    }


    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResEventResultEffect> getList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResEventResultEffect.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResEventResultEffect resEventResultEffect, Persistent persistent) throws StoreException {
        $(resEventResultEffect).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResEventResultEffect resEventResultEffect) throws StoreException {
        $(resEventResultEffect).delete();
    }
}
