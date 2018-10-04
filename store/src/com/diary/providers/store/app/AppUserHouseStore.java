package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.AppUserHouse;
import com.diary.entity.app.AppUserLimit;
import com.diary.entity.app.AppUserMan;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserHouseStore {


    AppUserHouse getById(Long id, Selector... selectors) throws StoreException;


    Page<AppUserHouse> getPageList(int start,
                                 int limit, List<Selector> selectorList) throws StoreException;

    List<AppUserHouse> getByUserId(Long userId) throws StoreException;

    List<AppUserHouse> getByUserIdHouseId(Long userId,Long houseId) throws StoreException;

    void save(AppUserHouse appUserHouse, Persistent persistent) throws StoreException;

    void buy(AppUserHouse appUserHouse, Persistent persistent, AppUserMan appUserMan,AppUserLimit appUserLimit) throws StoreException;

    void sell(AppUserHouse appUserHouse, AppUserMan appUserMan,AppUserLimit appUserLimit) throws StoreException;

    void delete(AppUserHouse appUserHouse) throws StoreException;

}
