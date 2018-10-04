package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserLimit;
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

    List<AppUserLuxury> getByUserIdLuxuryId(Long userId,Long luxuryId) throws StoreException;

    void save(AppUserLuxury appUserLuxury, Persistent persistent) throws StoreException;

    void buy(AppUserLuxury appUserLuxury, Persistent persistent, AppUserLady appUserLady, AppUserLimit appUserLimit) throws StoreException;

    void sell(AppUserLuxury appUserLuxury, AppUserLady appUserLady,AppUserLimit appUserLimit) throws StoreException;

    void delete(AppUserLuxury appUserLuxury) throws StoreException;

}
