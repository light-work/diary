package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.AppUserCouple;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserLimit;
import com.diary.entity.app.AppUserMan;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserCoupleStore {


    AppUserCouple getById(Long id, Selector... selectors) throws StoreException;


    Page<AppUserCouple> getPageList(int start,
                                 int limit, List<Selector> selectorList) throws StoreException;

    AppUserCouple getByUserId(Long userId) throws StoreException;

    AppUserCouple getByCoupleId(Long coupleId) throws StoreException;

    void save(AppUserCouple appUserCouple, Persistent persistent, AppUserMan appUserMan, AppUserLimit appUserLimit) throws StoreException;

    void save(AppUserCouple appUserCouple, Persistent persistent, AppUserLady appUserLady, AppUserLimit appUserLimit) throws StoreException;

    void delete(AppUserCouple appUserCouple) throws StoreException;

    void delete(AppUserCouple appUserCouple,AppUserMan appUserMan, AppUserLimit appUserLimit) throws StoreException;

    void delete(AppUserCouple appUserCouple,AppUserLady appUserLady, AppUserLimit appUserLimit) throws StoreException;

    void deleteFire(AppUserCouple appUserCouple,AppUserCouple appCoupleUserCouple, AppUserMan appUserMan, AppUserLimit appUserLimit) throws StoreException;

    void deleteFire(AppUserCouple appUserCouple,AppUserCouple appCoupleUserCouple, AppUserLady appUserLady, AppUserLimit appUserLimit) throws StoreException;

}
