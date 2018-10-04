package com.diary.storeImpl.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserLady;
import com.diary.entity.app.AppUserLimit;
import com.diary.entity.app.AppUserMan;
import com.diary.entity.app.AppUserPlan;
import com.diary.providers.store.app.AppUserPlanStore;
import com.diary.service.app.AppUserPlanService;
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
public class AppUserPlanStoreImpl implements AppUserPlanStore {

    @Inject
    private AppUserPlanService appUserPlanService;


    @Override
    @ConnectManager
    public AppUserPlan getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.appUserPlanService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public List<AppUserPlan> getByUserId(Long userId) throws StoreException {
        try {
            return this.appUserPlanService.getByUserId(userId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<AppUserPlan> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.appUserPlanService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public void save(AppUserPlan appUserPlan, Persistent persistent,AppUserMan appUserMan) throws StoreException {
        try {
            this.appUserPlanService.save(appUserPlan, persistent,appUserMan);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(AppUserPlan appUserPlan, Persistent persistent,AppUserLady appUserLady) throws StoreException {
        try {
            this.appUserPlanService.save(appUserPlan, persistent,appUserLady);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public void delete(AppUserPlan appUserPlan) throws StoreException {
        try {
            this.appUserPlanService.delete(appUserPlan);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
