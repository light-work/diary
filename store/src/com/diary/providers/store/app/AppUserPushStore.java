package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.AppUserForm;
import com.diary.entity.app.AppUserPush;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserPushStore {

    AppUserPush getById(Long id, Selector... selectors) throws StoreException;

    List<AppUserPush> getByUserId(Long userId, Integer year, Integer month, Integer day) throws StoreException;

    void save(AppUserPush appUserPush, Persistent persistent) throws StoreException;

    void save(AppUserPush appUserPush, Persistent persistent, AppUserForm appUserForm) throws StoreException;

    void save(List<AppUserPush> appUserPushs, Persistent persistent) throws StoreException;

    void delete(List<AppUserPush> appUserPushList) throws StoreException;
}
