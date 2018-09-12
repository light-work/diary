package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResClothesEffect;
import com.diary.providers.store.res.ResClothesEffectStore;
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
public class ResClothesEffectService extends HQuery implements ResClothesEffectStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public ResClothesEffect getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResClothesEffect.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResClothesEffect> getListByClothesId(Long clothesId) throws StoreException {
        return $($alias("clothesId", "clothesId"), $eq("clothesId.id", clothesId)).list(ResClothesEffect.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResClothesEffect> getList(List<Selector> selectorList) throws StoreException {
        return $(selectorList).list(ResClothesEffect.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResClothesEffect resClothesEffect, Persistent persistent) throws StoreException {
        $(resClothesEffect).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResClothesEffect resClothesEffect) throws StoreException {
        $(resClothesEffect).delete();
    }
}
