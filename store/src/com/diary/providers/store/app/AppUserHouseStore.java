package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.AppUserHouse;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserHouseStore {


    AppUserHouse getById(Long id, Selector... selectors) throws StoreException;


    Page<AppUserHouse> getPageList(int start,
                                 int limit, List<Selector> selectorList) throws StoreException;

    List<AppUserHouse> getByUserId(Long userId) throws StoreException;

    void save(AppUserHouse appUserHouse, Persistent persistent) throws StoreException;

    void delete(AppUserHouse appUserHouse) throws StoreException;

}
