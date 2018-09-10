package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResCoupleEffect;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResCoupleEffectStore {


    ResCoupleEffect getById(Long id, Selector... selectors) throws StoreException;

    List<ResCoupleEffect> getListByCoupleId(Long coupleId) throws StoreException;

    void save(ResCoupleEffect resCoupleEffect, Persistent persistent) throws StoreException;

    void delete(ResCoupleEffect resCoupleEffect) throws StoreException;

}
