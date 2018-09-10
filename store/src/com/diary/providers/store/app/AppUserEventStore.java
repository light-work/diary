package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.AppUserEvent;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserEventStore {


    AppUserEvent getById(Long id, Selector... selectors) throws StoreException;


    Page<AppUserEvent> getPageList(int start,
                                     int limit, List<Selector> selectorList) throws StoreException;

    List<AppUserEvent> getByUserId(Long userId) throws StoreException;

    void save(AppUserEvent appUserEvent, Persistent persistent) throws StoreException;

    void delete(AppUserEvent appUserEvent) throws StoreException;

}
