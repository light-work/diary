package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResEventStore {


    ResEvent getById(Long id, Selector... selectors) throws StoreException;

    Page<ResEvent> getPageList(int start,
                             int limit, List<Selector> selectorList) throws StoreException;

    List<ResEvent> getList(List<Selector> selectorList) throws StoreException;

    void save(ResEvent resEvent, Persistent persistent) throws StoreException;

    void delete(ResEvent resEvent) throws StoreException;

}
