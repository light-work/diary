package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEventResult;
import com.diary.providers.store.res.ResEventResultStore;
import com.diary.service.res.ResEventResultService;
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
public class ResEventResultStoreImpl implements ResEventResultStore {

    @Inject
    private ResEventResultService resEventResultService;


    @Override
    @ConnectManager
    public ResEventResult getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resEventResultService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResEventResult> getListByEventId(Long eventId) throws StoreException {
        try {
            return this.resEventResultService.getListByEventId(eventId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResEventResult> getList(List<Selector> selectorList) throws StoreException {
        try {
            return this.resEventResultService.getList(selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public ResEventResult getByOrder(Integer displayOrder) throws StoreException {
        try {
            return this.resEventResultService.getByOrder(displayOrder);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Integer getMaxOrder() throws StoreException {
        try {
            return this.resEventResultService.getMaxOrder();
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResEventResult resEventResult) throws StoreException {
        try {
            this.resEventResultService.delete(resEventResult);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResEventResult appUser, Persistent persistent) throws StoreException {
        try {
            this.resEventResultService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void saveOrder(ResEventResult resEventResult, Persistent persistent, ResEventResult resEventResultOrder) throws StoreException {
        try {
            this.resEventResultService.saveOrder(resEventResult, persistent, resEventResultOrder);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
