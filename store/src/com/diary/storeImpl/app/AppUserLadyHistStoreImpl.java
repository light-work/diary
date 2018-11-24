package com.diary.storeImpl.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserLadyHist;
import com.diary.providers.store.app.AppUserLadyHistStore;
import com.diary.service.app.AppUserLadyHistService;
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
public class AppUserLadyHistStoreImpl implements AppUserLadyHistStore {

    @Inject
    private AppUserLadyHistService appUserLadyHistService;


    @Override
    @ConnectManager
    public AppUserLadyHist getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.appUserLadyHistService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public AppUserLadyHist getByUserId(Long userId) throws StoreException {
        try {
            return this.appUserLadyHistService.getByUserId(userId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<AppUserLadyHist> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.appUserLadyHistService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public void save(AppUserLadyHist appUserLadyHist, Persistent persistent) throws StoreException {
        try {
            this.appUserLadyHistService.save(appUserLadyHist, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

}
