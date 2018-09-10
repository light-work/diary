package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResEventNo;
import com.diary.entity.res.ResEventYes;
import com.diary.entity.res.ResCoupleEvent;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResCoupleEventStore {


    ResCoupleEvent getById(Long id, Selector... selectors) throws StoreException;

    List<ResCoupleEvent> getListByCoupleId(Long jobId) throws StoreException;

    void save(ResCoupleEvent resCoupleEvent, Persistent persistent) throws StoreException;

    void save(ResCoupleEvent resCoupleEvent, Persistent persistent, ResEvent resEvent, Persistent resEventPersistent,
              ResEventYes resEventYes, Persistent resEventYesPersistent,
              ResEventNo resEventNo, Persistent resEventNoPersistent) throws StoreException;

    void delete(ResCoupleEvent resCoupleEvent) throws StoreException;

}
