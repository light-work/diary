package com.diary.storeImpl.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserRankings;
import com.diary.providers.store.app.AppUserRankingsStore;
import com.diary.service.app.AppUserRankingsService;
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
public class AppUserRankingsStoreImpl implements AppUserRankingsStore {

    @Inject
    private AppUserRankingsService appUserRankingsService;


    @Override
    @ConnectManager
    public AppUserRankings getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.appUserRankingsService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public AppUserRankings getByUserId(Long userId) throws StoreException {
        try {
            return this.appUserRankingsService.getByUserId(userId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Integer getCount() throws StoreException {
        try {
            return this.appUserRankingsService.getCount();
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Integer getMin() throws StoreException {
        try {
            return this.appUserRankingsService.getMin();
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<AppUserRankings> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.appUserRankingsService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public void save(AppUserRankings appUserRankings, Persistent persistent) throws StoreException {
        try {
            this.appUserRankingsService.save(appUserRankings, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(AppUserRankings appUserRankings) throws StoreException {
        try {
            this.appUserRankingsService.delete(appUserRankings);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(List<AppUserRankings> appUserRankingsList) throws StoreException {
        try {
            this.appUserRankingsService.delete(appUserRankingsList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
