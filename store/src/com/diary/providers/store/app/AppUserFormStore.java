package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.*;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserFormStore {

    AppUserForm getById(Long id, Selector... selectors) throws StoreException;

    List<AppUserForm> getByUserId(Long userId,Integer year,Integer month,Integer day) throws StoreException;

    void save(AppUserForm appUserForm, Persistent persistent) throws StoreException;

    void save(List<AppUserForm> appUserForms, Persistent persistent) throws StoreException;

    void delete(List<AppUserForm> appUserFormList) throws StoreException;
}
