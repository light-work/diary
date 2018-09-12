package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResCarEffect;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResCarEffectStore {


    ResCarEffect getById(Long id, Selector... selectors) throws StoreException;

    List<ResCarEffect> getListByCarId(Long carId) throws StoreException;

    List<ResCarEffect> getList(List<Selector> selectorList) throws StoreException;


    void save(ResCarEffect resCarEffect, Persistent persistent) throws StoreException;

    void delete(ResCarEffect resCarEffect) throws StoreException;

}
