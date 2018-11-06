package com.diary.storeImpl.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserLuck;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserLimit;
import com.diary.entity.app.AppUserMan;
import com.diary.providers.store.app.AppUserLuckStore;
import com.diary.service.app.AppUserLuckService;
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
public class AppUserLuckStoreImpl implements AppUserLuckStore {

    @Inject
    private AppUserLuckService appUserLuckService;


    @Override
    @ConnectManager
    public AppUserLuck getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.appUserLuckService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public List<AppUserLuck> getByUserId(Long userId) throws StoreException {
        try {
            return this.appUserLuckService.getByUserId(userId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<AppUserLuck> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.appUserLuckService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public void save(AppUserLuck appUserLuck, Persistent persistent,AppUserMan appUserMan,AppUserLimit appUserLimit) throws StoreException {
        try {
            this.appUserLuckService.save(appUserLuck, persistent,appUserMan,appUserLimit);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(AppUserLuck appUserLuck, Persistent persistent, AppUserLady appUserLady,AppUserLimit appUserLimit) throws StoreException {
        try {
            this.appUserLuckService.save(appUserLuck, persistent,appUserLady,appUserLimit);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public void delete(AppUserLuck appUserLuck) throws StoreException {
        try {
            this.appUserLuckService.delete(appUserLuck);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public void delete(List<AppUserLuck> appUserLuckList) throws StoreException {
        try {
            this.appUserLuckService.delete(appUserLuckList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
