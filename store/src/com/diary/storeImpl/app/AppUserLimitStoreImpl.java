package com.diary.storeImpl.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserMan;
import com.diary.entity.app.AppUserLimit;
import com.diary.providers.store.app.AppUserLimitStore;
import com.diary.service.app.AppUserLimitService;
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
public class AppUserLimitStoreImpl implements AppUserLimitStore {

    @Inject
    private AppUserLimitService appUserLimitService;

    @Override
    @ConnectManager
    public List<AppUserLimit> getListByUserId(Long userId) throws StoreException {
        try {
            return this.appUserLimitService.getListByUserId(userId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Integer getCountByUserIdDayAction(Long userId, Integer day, String action) throws StoreException {
        try {
            return this.appUserLimitService.getCountByUserIdDayAction(userId,day,action);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(AppUserLimit appUserLimit, Persistent persistent) throws StoreException {
        try {
            this.appUserLimitService.save(appUserLimit, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public void delete(AppUserLimit appUserLimit) throws StoreException {
        try {
            this.appUserLimitService.delete(appUserLimit);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(List<AppUserLimit> appUserLimitList) throws StoreException {
        try {
            this.appUserLimitService.delete(appUserLimitList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
