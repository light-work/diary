package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEventNoResult;
import com.diary.providers.store.res.ResEventNoResultStore;
import com.diary.service.res.ResEventNoResultService;
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
public class ResEventNoResultStoreImpl implements ResEventNoResultStore {

    @Inject
    private ResEventNoResultService resEventNoResultService;


    @Override
    @ConnectManager
    public ResEventNoResult getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resEventNoResultService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public List<ResEventNoResult> getByNoId(Long noId) throws StoreException {
        try {
            return this.resEventNoResultService.getByNoId(noId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResEventNoResult> getByList(List<Selector> selectorList) throws StoreException {
        try {
            return this.resEventNoResultService.getByList(selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResEventNoResult resEventNoResult, Persistent persistent) throws StoreException {
        try {
             this.resEventNoResultService.save(resEventNoResult, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResEventNoResult resEventNoResult) throws StoreException {
        try {
            this.resEventNoResultService.delete(resEventNoResult);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


}
