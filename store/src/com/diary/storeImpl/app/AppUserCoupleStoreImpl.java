package com.diary.storeImpl.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserCouple;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserLimit;
import com.diary.entity.app.AppUserMan;
import com.diary.providers.store.app.AppUserCoupleStore;
import com.diary.service.app.AppUserCoupleService;
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
public class AppUserCoupleStoreImpl implements AppUserCoupleStore {

    @Inject
    private AppUserCoupleService appUserCoupleService;


    @Override
    @ConnectManager
    public AppUserCouple getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.appUserCoupleService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public AppUserCouple getByUserId(Long userId) throws StoreException {
        try {
            return this.appUserCoupleService.getByUserId(userId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public AppUserCouple getByCoupleId(Long coupleId) throws StoreException {
        try {
            return this.appUserCoupleService.getByCoupleId(coupleId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<AppUserCouple> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.appUserCoupleService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public void save(AppUserCouple appUserCouple, Persistent persistent, AppUserMan appUserMan, AppUserLimit appUserLimit) throws StoreException {
        try {
            this.appUserCoupleService.save(appUserCouple, persistent,appUserMan,appUserLimit);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(AppUserCouple appUserCouple, Persistent persistent, AppUserLady appUserLady, AppUserLimit appUserLimit) throws StoreException {
        try {
            this.appUserCoupleService.save(appUserCouple, persistent,appUserLady,appUserLimit);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(AppUserCouple appUserCouple, AppUserMan appUserMan, AppUserLimit appUserLimit) throws StoreException {
        try {
            this.appUserCoupleService.delete(appUserCouple,appUserMan,appUserLimit);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(AppUserCouple appUserCouple, AppUserLady appUserLady, AppUserLimit appUserLimit) throws StoreException {
        try {
            this.appUserCoupleService.delete(appUserCouple,appUserLady,appUserLimit);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(AppUserCouple appUserCouple) throws StoreException {
        try {
            this.appUserCoupleService.delete(appUserCouple);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void deleteFire(AppUserCouple appUserCouple, AppUserCouple appCoupleUserCouple, AppUserMan appUserMan, AppUserLimit appUserLimit) throws StoreException {
        try {
            this.appUserCoupleService.deleteFire(appUserCouple, appCoupleUserCouple, appUserMan, appUserLimit);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void deleteFire(AppUserCouple appUserCouple, AppUserCouple appCoupleUserCouple, AppUserLady appUserLady, AppUserLimit appUserLimit) throws StoreException {
        try {
            this.appUserCoupleService.deleteFire(appUserCouple, appCoupleUserCouple, appUserLady, appUserLimit);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
