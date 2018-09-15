package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEvent;
import com.diary.providers.store.res.ResEventStore;
import com.diary.service.res.ResEventService;
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
public class ResEventStoreImpl implements ResEventStore {

    @Inject
    private ResEventService resEventService;


    @Override
    @ConnectManager
    public ResEvent getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resEventService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<ResEvent> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.resEventService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResEvent resEvent) throws StoreException {
        try {
            this.resEventService.delete(resEvent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResEvent resEvent, Persistent persistent) throws StoreException {
        try {
            this.resEventService.save(resEvent, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
