package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResCoupleEffect;
import com.diary.providers.store.res.ResCoupleEffectStore;
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
public class ResCoupleEffectService extends HQuery implements ResCoupleEffectStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResCoupleEffect getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResCoupleEffect.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResCoupleEffect> getListByCoupleId(Long coupleId) throws StoreException {
        return $($alias("coupleId", "coupleId"), $eq("coupleId.id", coupleId)).list(ResCoupleEffect.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResCoupleEffect resCoupleEffect, Persistent persistent) throws StoreException {
        $(resCoupleEffect).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResCoupleEffect resCoupleEffect) throws StoreException {
        $(resCoupleEffect).delete();
    }
}
