package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserMan;
import com.diary.entity.app.AppUserPlan;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserPlanStore {


    AppUserPlan getById(Long id, Selector... selectors) throws StoreException;


    Page<AppUserPlan> getPageList(int start,
                                 int limit, List<Selector> selectorList) throws StoreException;

    List<AppUserPlan> getByUserId(Long userId) throws StoreException;

    void save(AppUserPlan appUserPlan, Persistent persistent,AppUserMan appUserMan) throws StoreException;

    void save(AppUserPlan appUserPlan, Persistent persistent,AppUserLady appUserLady) throws StoreException;

    void delete(AppUserPlan appUserPlan) throws StoreException;

}
