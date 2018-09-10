package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResEventNo;
import com.diary.entity.res.ResEventYes;
import com.diary.entity.res.ResPlanEvent;
import com.diary.providers.store.res.ResPlanEventStore;
import com.diary.service.res.ResPlanEventService;
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
public class ResPlanEventStoreImpl implements ResPlanEventStore {

    @Inject
    private ResPlanEventService resPlanEventService;


    @Override
    @ConnectManager
    public ResPlanEvent getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resPlanEventService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResPlanEvent> getListByPlanId(Long jobId) throws StoreException {
        try {
            return this.resPlanEventService.getListByPlanId(jobId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }




    @Override
    @ConnectManager
    public void delete(ResPlanEvent resPlanEvent) throws StoreException {
        try {
            this.resPlanEventService.delete(resPlanEvent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResPlanEvent appUser, Persistent persistent) throws StoreException {
        try {
            this.resPlanEventService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResPlanEvent resPlanEvent, Persistent persistent, ResEvent resEvent, Persistent resEventPersistent, ResEventYes resEventYes, Persistent resEventYesPersistent, ResEventNo resEventNo, Persistent resEventNoPersistent) throws StoreException {
        try {
            this.resPlanEventService.save(resPlanEvent, persistent, resEvent, resEventPersistent, resEventYes, resEventYesPersistent, resEventNo, resEventNoPersistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
