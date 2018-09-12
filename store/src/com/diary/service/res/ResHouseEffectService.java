package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResHouseEffect;
import com.diary.providers.store.res.ResHouseEffectStore;
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
public class ResHouseEffectService extends HQuery implements ResHouseEffectStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResHouseEffect getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResHouseEffect.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResHouseEffect> getListByHouseId(Long houseId) throws StoreException {
        return $($alias("houseId", "houseId"), $eq("houseId.id", houseId)).list(ResHouseEffect.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResHouseEffect> getList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResHouseEffect.class);
    }
    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResHouseEffect resHouseEffect, Persistent persistent) throws StoreException {
        $(resHouseEffect).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResHouseEffect resHouseEffect) throws StoreException {
        $(resHouseEffect).delete();
    }
}
