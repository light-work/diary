package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.AppUserLady;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserLadyStore {


    AppUserLady getById(Long id, Selector... selectors) throws StoreException;


    Page<AppUserLady> getPageList(int start,
                                 int limit, List<Selector> selectorList) throws StoreException;
    
    AppUserLady getByUserId(Long userId) throws StoreException;

    void save(AppUserLady appUserLady, Persistent persistent) throws StoreException;

}
