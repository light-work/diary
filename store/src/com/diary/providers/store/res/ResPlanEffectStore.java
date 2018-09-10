package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResPlanEffect;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResPlanEffectStore {


    ResPlanEffect getById(Long id, Selector... selectors) throws StoreException;

    List<ResPlanEffect> getListByPlanId(Long planId) throws StoreException;

    void save(ResPlanEffect resPlanEffect, Persistent persistent) throws StoreException;

    void delete(ResPlanEffect resPlanEffect) throws StoreException;

}
