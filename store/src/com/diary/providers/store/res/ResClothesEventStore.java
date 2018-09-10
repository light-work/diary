package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResEventNo;
import com.diary.entity.res.ResEventYes;
import com.diary.entity.res.ResClothesEvent;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResClothesEventStore {


    ResClothesEvent getById(Long id, Selector... selectors) throws StoreException;

    List<ResClothesEvent> getListByClothesId(Long jobId) throws StoreException;

    void save(ResClothesEvent resClothesEvent, Persistent persistent) throws StoreException;

    void save(ResClothesEvent resClothesEvent, Persistent persistent, ResEvent resEvent, Persistent resEventPersistent,
              ResEventYes resEventYes, Persistent resEventYesPersistent,
              ResEventNo resEventNo, Persistent resEventNoPersistent) throws StoreException;

    void delete(ResClothesEvent resClothesEvent) throws StoreException;

}
