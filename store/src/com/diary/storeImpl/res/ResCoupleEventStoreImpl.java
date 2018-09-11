package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResEventNo;
import com.diary.entity.res.ResEventYes;
import com.diary.entity.res.ResCoupleEvent;
import com.diary.providers.store.res.ResCoupleEventStore;
import com.diary.service.res.ResCoupleEventService;
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
public class ResCoupleEventStoreImpl implements ResCoupleEventStore {

    @Inject
    private ResCoupleEventService resCoupleEventService;


    @Override
    @ConnectManager
    public ResCoupleEvent getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resCoupleEventService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResCoupleEvent> getList(List<Selector> selectorList) throws StoreException {
        try {
            return this.resCoupleEventService.getList(selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResCoupleEvent> getListByCoupleId(Long jobId) throws StoreException {
        try {
            return this.resCoupleEventService.getListByCoupleId(jobId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResCoupleEvent resCoupleEvent) throws StoreException {
        try {
            this.resCoupleEventService.delete(resCoupleEvent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResCoupleEvent appUser, Persistent persistent) throws StoreException {
        try {
            this.resCoupleEventService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResCoupleEvent resCoupleEvent, Persistent persistent, ResEvent resEvent, Persistent resEventPersistent, ResEventYes resEventYes, Persistent resEventYesPersistent, ResEventNo resEventNo, Persistent resEventNoPersistent) throws StoreException {
        try {
            this.resCoupleEventService.save(resCoupleEvent, persistent, resEvent, resEventPersistent, resEventYes, resEventYesPersistent, resEventNo, resEventNoPersistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
