package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResPlanEvent;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResPlanEventStore {


    ResPlanEvent getById(Long id, Selector... selectors) throws StoreException;

    Page<ResPlanEvent> getPageList(int start,
                                   int limit, List<Selector> selectorList) throws StoreException;

    List<ResPlanEvent> getListByPlanId(Long jobId) throws StoreException;

    List<ResPlanEvent> getList(List<Selector> selectorList) throws StoreException;

    void save(ResPlanEvent resPlanEvent, Persistent persistent) throws StoreException;

    void save(ResPlanEvent resPlanEvent, Persistent persistent, ResEvent resEvent, Persistent resEventPersistent) throws StoreException;

    void delete(ResPlanEvent resPlanEvent) throws StoreException;

}
