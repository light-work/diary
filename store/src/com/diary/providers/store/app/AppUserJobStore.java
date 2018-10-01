package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.AppUserJob;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserMan;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserJobStore {


    AppUserJob getById(Long id, Selector... selectors) throws StoreException;


    Page<AppUserJob> getPageList(int start,
                                    int limit, List<Selector> selectorList) throws StoreException;

    AppUserJob getByUserId(Long userId) throws StoreException;

    void save(AppUserJob appUserJob, Persistent persistent, AppUserMan appUserMan) throws StoreException;

    void save(AppUserJob appUserJob, Persistent persistent, AppUserLady appUserLady) throws StoreException;

    void delete(AppUserJob appUserJob) throws StoreException;

}
