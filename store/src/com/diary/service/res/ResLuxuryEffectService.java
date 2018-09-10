package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResLuxuryEffect;
import com.diary.providers.store.res.ResLuxuryEffectStore;
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
public class ResLuxuryEffectService extends HQuery implements ResLuxuryEffectStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResLuxuryEffect getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResLuxuryEffect.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResLuxuryEffect> getListByLuxuryId(Long luxuryId) throws StoreException {
        return $($alias("luxuryId", "luxuryId"), $eq("luxuryId.id", luxuryId)).list(ResLuxuryEffect.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResLuxuryEffect resLuxuryEffect, Persistent persistent) throws StoreException {
        $(resLuxuryEffect).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResLuxuryEffect resLuxuryEffect) throws StoreException {
        $(resLuxuryEffect).delete();
    }
}
