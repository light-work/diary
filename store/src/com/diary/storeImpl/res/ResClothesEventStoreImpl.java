package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResClothesEvent;
import com.diary.providers.store.res.ResClothesEventStore;
import com.diary.service.res.ResClothesEventService;
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
public class ResClothesEventStoreImpl implements ResClothesEventStore {

    @Inject
    private ResClothesEventService resClothesEventService;


    @Override
    @ConnectManager
    public ResClothesEvent getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resClothesEventService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResClothesEvent> getListByClothesId(Long jobId) throws StoreException {
        try {
            return this.resClothesEventService.getListByClothesId(jobId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResClothesEvent resClothesEvent) throws StoreException {
        try {
            this.resClothesEventService.delete(resClothesEvent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResClothesEvent appUser, Persistent persistent) throws StoreException {
        try {
            this.resClothesEventService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResClothesEvent resClothesEvent, Persistent persistent, ResEvent resEvent, Persistent resEventPersistent) throws StoreException {
        try {
            this.resClothesEventService.save(resClothesEvent, persistent, resEvent, resEventPersistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
