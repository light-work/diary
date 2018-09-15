package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResHouseEvent;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResHouseEventStore {


    ResHouseEvent getById(Long id, Selector... selectors) throws StoreException;

    List<ResHouseEvent> getListByHouseId(Long jobId) throws StoreException;

    void save(ResHouseEvent resHouseEvent, Persistent persistent) throws StoreException;

    void save(ResHouseEvent resHouseEvent, Persistent persistent, ResEvent resEvent, Persistent resEventPersistent) throws StoreException;

    void delete(ResHouseEvent resHouseEvent) throws StoreException;

}
