package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResCarEffect;
import com.diary.providers.store.res.ResCarEffectStore;
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
public class ResCarEffectService extends HQuery implements ResCarEffectStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResCarEffect getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResCarEffect.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResCarEffect> getListByCarId(Long carId) throws StoreException {
        return $($alias("carId", "carId"), $eq("carId.id", carId)).list(ResCarEffect.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResCarEffect> getList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResCarEffect.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResCarEffect resCarEffect, Persistent persistent) throws StoreException {
        $(resCarEffect).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResCarEffect resCarEffect) throws StoreException {
        $(resCarEffect).delete();
    }
}
