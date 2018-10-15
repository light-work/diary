package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResEventResult;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResEventResultStore {


    ResEventResult getById(Long id, Selector... selectors) throws StoreException;

    ResEventResult getByOrder(Integer displayOrder) throws StoreException;


    List<ResEventResult> getListByEventId(Long eventId) throws StoreException;

    Integer getMaxOrder() throws StoreException;

    List<ResEventResult> getList(List<Selector> selectorList) throws StoreException;

    void save(ResEventResult resEventResult, Persistent persistent) throws StoreException;

    void saveOrder(ResEventResult resEventResult, Persistent persistent,ResEventResult resEventResultOrder) throws StoreException;

    void delete(ResEventResult resEventResult) throws StoreException;

}
