package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.AppUserLadyHist;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserLadyHistStore {


    AppUserLadyHist getById(Long id, Selector... selectors) throws StoreException;


    Page<AppUserLadyHist> getPageList(int start,
                                     int limit, List<Selector> selectorList) throws StoreException;

    AppUserLadyHist getByUserId(Long userId) throws StoreException;

    void save(AppUserLadyHist appUserLadyHist, Persistent persistent) throws StoreException;


}
