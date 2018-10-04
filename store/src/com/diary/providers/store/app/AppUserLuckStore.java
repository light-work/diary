package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.AppUserLuck;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserLimit;
import com.diary.entity.app.AppUserMan;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserLuckStore {


    AppUserLuck getById(Long id, Selector... selectors) throws StoreException;


    Page<AppUserLuck> getPageList(int start,
                                 int limit, List<Selector> selectorList) throws StoreException;

    AppUserLuck getByUserId(Long userId) throws StoreException;

    void save(AppUserLuck appUserLuck, Persistent persistent, AppUserMan appUserMan, AppUserLimit appUserLimit) throws StoreException;

    void save(AppUserLuck appUserLuck, Persistent persistent, AppUserLady appUserLady, AppUserLimit appUserLimit) throws StoreException;

    void delete(AppUserLuck appUserLuck) throws StoreException;

}
