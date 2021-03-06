package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResCoupleEvent;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResCoupleEventStore {


    ResCoupleEvent getById(Long id, Selector... selectors) throws StoreException;

    List<ResCoupleEvent> getListByCoupleId(Long jobId) throws StoreException;

    List<ResCoupleEvent> getList(List<Selector> selectorList) throws StoreException;

    void save(ResCoupleEvent resCoupleEvent, Persistent persistent) throws StoreException;

    void save(ResCoupleEvent resCoupleEvent, Persistent persistent, ResEvent resEvent, Persistent resEventPersistent) throws StoreException;

    void delete(ResCoupleEvent resCoupleEvent) throws StoreException;

}
