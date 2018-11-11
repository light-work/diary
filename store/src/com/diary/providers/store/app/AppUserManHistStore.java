package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.*;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserManHistStore {


    AppUserManHist getById(Long id, Selector... selectors) throws StoreException;


    Page<AppUserManHist> getPageList(int start,
                                 int limit, List<Selector> selectorList) throws StoreException;

    AppUserManHist getByUserId(Long userId) throws StoreException;

    void save(AppUserManHist appUserManHist, Persistent persistent) throws StoreException;


}
