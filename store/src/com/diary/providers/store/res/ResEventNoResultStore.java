package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResEventNoResult;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResEventNoResultStore {


    ResEventNoResult getById(Long id, Selector... selectors) throws StoreException;

    List<ResEventNoResult> getByNoId(Long noId) throws StoreException;

    List<ResEventNoResult> getByList(List<Selector> selectorList) throws StoreException;

    void save(ResEventNoResult resEventNoResult, Persistent persistent) throws StoreException;

    void delete(ResEventNoResult resEventNoResult) throws StoreException;

}
