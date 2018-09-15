package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.entity.res.ResHouseEvent;
import com.diary.providers.store.res.ResHouseEventStore;
import com.diary.service.res.ResHouseEventService;
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
public class ResHouseEventStoreImpl implements ResHouseEventStore {

    @Inject
    private ResHouseEventService resHouseEventService;


    @Override
    @ConnectManager
    public ResHouseEvent getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resHouseEventService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResHouseEvent> getListByHouseId(Long jobId) throws StoreException {
        try {
            return this.resHouseEventService.getListByHouseId(jobId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResHouseEvent resHouseEvent) throws StoreException {
        try {
            this.resHouseEventService.delete(resHouseEvent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResHouseEvent appUser, Persistent persistent) throws StoreException {
        try {
            this.resHouseEventService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResHouseEvent resHouseEvent, Persistent persistent, ResEvent resEvent, Persistent resEventPersistent) throws StoreException {
        try {
            this.resHouseEventService.save(resHouseEvent, persistent, resEvent, resEventPersistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
