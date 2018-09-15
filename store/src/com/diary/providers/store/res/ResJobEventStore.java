package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResJobEvent;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResJobEventStore {


    ResJobEvent getById(Long id, Selector... selectors) throws StoreException;

    List<ResJobEvent> getListByJobId(Long jobId) throws StoreException;

    List<ResJobEvent> getList(List<Selector> selectorList) throws StoreException;

    void save(ResJobEvent resJobEvent, Persistent persistent) throws StoreException;

    void save(ResJobEvent resJobEvent, Persistent persistent,ResEvent resEvent,Persistent resEventPersistent) throws StoreException;

    void delete(ResJobEvent resJobEvent) throws StoreException;

}
