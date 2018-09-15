package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResLuxuryEvent;
import com.diary.providers.store.res.ResLuxuryEventStore;
import com.diary.service.res.ResLuxuryEventService;
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
public class ResLuxuryEventStoreImpl implements ResLuxuryEventStore {

    @Inject
    private ResLuxuryEventService resLuxuryEventService;


    @Override
    @ConnectManager
    public ResLuxuryEvent getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resLuxuryEventService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResLuxuryEvent> getListByLuxuryId(Long jobId) throws StoreException {
        try {
            return this.resLuxuryEventService.getListByLuxuryId(jobId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResLuxuryEvent resLuxuryEvent) throws StoreException {
        try {
            this.resLuxuryEventService.delete(resLuxuryEvent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResLuxuryEvent appUser, Persistent persistent) throws StoreException {
        try {
            this.resLuxuryEventService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResLuxuryEvent resLuxuryEvent, Persistent persistent, ResEvent resEvent, Persistent resEventPersistent) throws StoreException {
        try {
            this.resLuxuryEventService.save(resLuxuryEvent, persistent, resEvent, resEventPersistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
