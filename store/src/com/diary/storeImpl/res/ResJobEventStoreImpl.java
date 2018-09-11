package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResEventNo;
import com.diary.entity.res.ResEventYes;
import com.diary.entity.res.ResJobEvent;
import com.diary.providers.store.res.ResJobEventStore;
import com.diary.service.res.ResJobEventService;
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
public class ResJobEventStoreImpl implements ResJobEventStore {

    @Inject
    private ResJobEventService resJobEventService;


    @Override
    @ConnectManager
    public ResJobEvent getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resJobEventService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResJobEvent> getList(List<Selector> selectorList) throws StoreException {
        try {
            return this.resJobEventService.getList(selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResJobEvent> getListByJobId(Long jobId) throws StoreException {
        try {
            return this.resJobEventService.getListByJobId(jobId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResJobEvent resJobEvent) throws StoreException {
        try {
            this.resJobEventService.delete(resJobEvent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResJobEvent appUser, Persistent persistent) throws StoreException {
        try {
            this.resJobEventService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResJobEvent resJobEvent, Persistent persistent, ResEvent resEvent, Persistent resEventPersistent, ResEventYes resEventYes, Persistent resEventYesPersistent, ResEventNo resEventNo, Persistent resEventNoPersistent) throws StoreException {
        try {
            this.resJobEventService.save(resJobEvent, persistent, resEvent, resEventPersistent, resEventYes, resEventYesPersistent, resEventNo, resEventNoPersistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
