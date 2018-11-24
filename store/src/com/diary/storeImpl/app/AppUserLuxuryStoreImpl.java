package com.diary.storeImpl.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserLimit;
import com.diary.entity.app.AppUserLuxury;
import com.diary.providers.store.app.AppUserLuxuryStore;
import com.diary.service.app.AppUserLuxuryService;
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
public class AppUserLuxuryStoreImpl implements AppUserLuxuryStore {

    @Inject
    private AppUserLuxuryService appUserLuxuryService;


    @Override
    @ConnectManager
    public AppUserLuxury getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.appUserLuxuryService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public List<AppUserLuxury> getByUserId(Long userId) throws StoreException {
        try {
            return this.appUserLuxuryService.getByUserId(userId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<AppUserLuxury> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.appUserLuxuryService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<AppUserLuxury> getByUserIdLuxuryId(Long userId, Long luxuryId) throws StoreException {
        try {
            return this.appUserLuxuryService.getByUserIdLuxuryId(userId, luxuryId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(AppUserLuxury appUserLuxury, Persistent persistent) throws StoreException {
        try {
            this.appUserLuxuryService.save(appUserLuxury, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public void delete(AppUserLuxury appUserLuxury) throws StoreException {
        try {
            this.appUserLuxuryService.delete(appUserLuxury);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void buy(AppUserLuxury appUserLuxury, Persistent persistent, AppUserLady appUserLady, AppUserLimit appUserLimit) throws StoreException {
        try {
            this.appUserLuxuryService.buy(appUserLuxury, persistent, appUserLady, appUserLimit);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void sell(AppUserLuxury appUserLuxury, AppUserLady appUserLady, AppUserLimit appUserLimit) throws StoreException {
        try {
            this.appUserLuxuryService.sell(appUserLuxury, appUserLady, appUserLimit);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(List<AppUserLuxury> appUserLuxuryList) throws StoreException {
        try {
            this.appUserLuxuryService.delete(appUserLuxuryList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
