package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResEventResult;
import com.diary.entity.res.ResEventResultEffect;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResEventResultEffectStore {


    ResEventResultEffect getById(Long id, Selector... selectors) throws StoreException;

    List<ResEventResultEffect> getList(List<Selector> selectorList) throws StoreException;

    List<ResEventResultEffect> getListByResultId(Long resultId) throws StoreException;

    void save(ResEventResultEffect resEventResultEffect, Persistent persistent) throws StoreException;

    void delete(ResEventResultEffect resEventResultEffect) throws StoreException;

}
