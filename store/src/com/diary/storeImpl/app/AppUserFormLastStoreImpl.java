package com.diary.storeImpl.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserFormLast;
import com.diary.providers.store.app.AppUserFormLastStore;
import com.diary.service.app.AppUserFormLastService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;
import org.guiceside.support.hsf.ConnectManager;
import org.hibernate.HibernateException;

import java.util.List;

/**
 * Created by Lara Croft on 2016/12/21.
 */
@Singleton
public class AppUserFormLastStoreImpl implements AppUserFormLastStore {

    @Inject
    private AppUserFormLastService appUserFormLastService;


    @Override
    @ConnectManager
    public AppUserFormLast getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.appUserFormLastService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public AppUserFormLast getByUserId(Long userId) throws StoreException {
        try {
            return this.appUserFormLastService.getByUserId(userId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(AppUserFormLast appUserFormLast, Persistent persistent) throws StoreException {
        try {
            this.appUserFormLastService.save(appUserFormLast, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(List<AppUserFormLast> appUserFormLasts, Persistent persistent) throws StoreException {
        try {
            this.appUserFormLastService.save(appUserFormLasts, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public void delete(List<AppUserFormLast> appUserFormLastList) throws StoreException {
        try {
            this.appUserFormLastService.delete(appUserFormLastList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
