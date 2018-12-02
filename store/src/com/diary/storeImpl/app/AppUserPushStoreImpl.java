package com.diary.storeImpl.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserForm;
import com.diary.entity.app.AppUserPush;
import com.diary.providers.store.app.AppUserPushStore;
import com.diary.service.app.AppUserPushService;
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
public class AppUserPushStoreImpl implements AppUserPushStore {

    @Inject
    private AppUserPushService appUserPushService;


    @Override
    @ConnectManager
    public AppUserPush getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.appUserPushService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<AppUserPush> getByUserId(Long userId,Integer year,Integer month,Integer day) throws StoreException {
        try {
            return this.appUserPushService.getByUserId(userId,year,month,day);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }




    @Override
    @ConnectManager
    public void save(AppUserPush appUserPush, Persistent persistent) throws StoreException {
        try {
            this.appUserPushService.save(appUserPush, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(List<AppUserPush> appUserPushs, Persistent persistent) throws StoreException {
        try {
            this.appUserPushService.save(appUserPushs, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(AppUserPush appUserPush, Persistent persistent, AppUserForm appUserForm) throws StoreException {
        try {
            this.appUserPushService.save(appUserPush, persistent, appUserForm);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(List<AppUserPush> appUserPushList) throws StoreException {
        try {
            this.appUserPushService.delete(appUserPushList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
