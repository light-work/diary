package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEventNo;
import com.diary.providers.store.res.ResEventNoStore;
import com.diary.service.res.ResEventNoService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;
import org.guiceside.support.hsf.ConnectManager;
import org.hibernate.HibernateException;

/**
 * Created by Lara Croft on 2016/12/21.
 */
@Singleton
public class ResEventNoStoreImpl implements ResEventNoStore {

    @Inject
    private ResEventNoService resEventNoService;


    @Override
    @ConnectManager
    public ResEventNo getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resEventNoService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public ResEventNo getByEventId(Long eventId) throws StoreException {
        try {
            return this.resEventNoService.getByEventId(eventId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResEventNo resEventNo, Persistent persistent) throws StoreException {
        try {
             this.resEventNoService.save(resEventNo, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResEventNo resEventNo) throws StoreException {
        try {
            this.resEventNoService.delete(resEventNo);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


}
