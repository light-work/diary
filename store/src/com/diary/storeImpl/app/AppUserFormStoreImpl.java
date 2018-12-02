package com.diary.storeImpl.app;

import com.diary.common.StoreException;
import com.diary.entity.app.*;
import com.diary.providers.store.app.AppUserFormStore;
import com.diary.service.app.AppUserFormService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;
import org.guiceside.support.hsf.ConnectManager;
import org.hibernate.HibernateException;

import java.util.List;

/**
 * Created by Lara Croft on 2016/12/21.
 */
@Singleton
public class AppUserFormStoreImpl implements AppUserFormStore {

    @Inject
    private AppUserFormService appUserFormService;


    @Override
    @ConnectManager
    public AppUserForm getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.appUserFormService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<AppUserForm> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.appUserFormService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<AppUserForm> getByUserId(Long userId,Integer year,Integer month,Integer day) throws StoreException {
        try {
            return this.appUserFormService.getByUserId(userId,year,month,day);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }




    @Override
    @ConnectManager
    public void save(AppUserForm appUserForm, Persistent persistent) throws StoreException {
        try {
            this.appUserFormService.save(appUserForm, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(List<AppUserForm> appUserForms, Persistent persistent) throws StoreException {
        try {
            this.appUserFormService.save(appUserForms, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(AppUserForm appUserForm, Persistent persistent, AppUserFormLast appUserFormLast, Persistent persistentLast) throws StoreException {
        try {
            this.appUserFormService.save(appUserForm, persistent, appUserFormLast, persistentLast);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(List<AppUserForm> appUserFormList) throws StoreException {
        try {
            this.appUserFormService.delete(appUserFormList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(AppUserForm appUserForm) throws StoreException {
        try {
            this.appUserFormService.delete(appUserForm);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
