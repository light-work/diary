package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.AppUserFormLast;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserFormLastStore {

    AppUserFormLast getById(Long id, Selector... selectors) throws StoreException;

    AppUserFormLast getByUserId(Long userId) throws StoreException;

    void save(AppUserFormLast appUserFormLast, Persistent persistent) throws StoreException;

    void save(List<AppUserFormLast> appUserFormLasts, Persistent persistent) throws StoreException;

    void delete(List<AppUserFormLast> appUserFormLastList) throws StoreException;
}
