package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.*;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserRankingsStore {


    AppUserRankings getById(Long id, Selector... selectors) throws StoreException;

    AppUserRankings getByUserId(Long userId) throws StoreException;

    Integer getCount() throws StoreException;

    Integer getMin() throws StoreException;

    Page<AppUserRankings> getPageList(int start,
                                 int limit, List<Selector> selectorList) throws StoreException;

    void save(AppUserRankings appUserRankings, Persistent persistent) throws StoreException;

    void delete(AppUserRankings appUserRankings) throws StoreException;

    void delete(List<AppUserRankings> appUserRankingsList) throws StoreException;
}
