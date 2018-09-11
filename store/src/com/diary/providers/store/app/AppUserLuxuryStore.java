package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.AppUserLuxury;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserLuxuryStore {


    AppUserLuxury getById(Long id, Selector... selectors) throws StoreException;


    Page<AppUserLuxury> getPageList(int start,
                                 int limit, List<Selector> selectorList) throws StoreException;

    List<AppUserLuxury> getByUserId(Long userId) throws StoreException;

    void save(AppUserLuxury appUserLuxury, Persistent persistent) throws StoreException;

    void delete(AppUserLuxury appUserLuxury) throws StoreException;

}
