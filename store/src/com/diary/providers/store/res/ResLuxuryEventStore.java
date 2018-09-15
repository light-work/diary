package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResLuxuryEvent;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResLuxuryEventStore {


    ResLuxuryEvent getById(Long id, Selector... selectors) throws StoreException;

    List<ResLuxuryEvent> getListByLuxuryId(Long jobId) throws StoreException;

    void save(ResLuxuryEvent resLuxuryEvent, Persistent persistent) throws StoreException;

    void save(ResLuxuryEvent resLuxuryEvent, Persistent persistent, ResEvent resEvent, Persistent resEventPersistent) throws StoreException;

    void delete(ResLuxuryEvent resLuxuryEvent) throws StoreException;

}
