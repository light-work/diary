package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResCarEvent;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResCarEventStore {


    ResCarEvent getById(Long id, Selector... selectors) throws StoreException;

    List<ResCarEvent> getListByCarId(Long jobId) throws StoreException;

    void save(ResCarEvent resCarEvent, Persistent persistent) throws StoreException;

    void save(ResCarEvent resCarEvent, Persistent persistent, ResEvent resEvent, Persistent resEventPersistent) throws StoreException;

    void delete(ResCarEvent resCarEvent) throws StoreException;

}
