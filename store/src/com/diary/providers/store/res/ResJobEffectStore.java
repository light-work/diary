package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResJobEffect;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResJobEffectStore {


    ResJobEffect getById(Long id, Selector... selectors) throws StoreException;

    List<ResJobEffect> getListByJobId(Long jobId) throws StoreException;

    List<ResJobEffect> getList(List<Selector> selectorList) throws StoreException;

    void save(ResJobEffect resJobEffect, Persistent persistent) throws StoreException;

    void delete(ResJobEffect resJobEffect) throws StoreException;

}
