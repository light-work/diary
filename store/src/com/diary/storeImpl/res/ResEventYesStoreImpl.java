package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEventYes;
import com.diary.providers.store.res.ResEventYesStore;
import com.diary.service.res.ResEventYesService;
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
public class ResEventYesStoreImpl implements ResEventYesStore {

    @Inject
    private ResEventYesService resEventYesService;


    @Override
    @ConnectManager
    public ResEventYes getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resEventYesService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public ResEventYes getByEventId(Long eventId) throws StoreException {
        try {
            return this.resEventYesService.getByEventId(eventId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResEventYes resEventYes, Persistent persistent) throws StoreException {
        try {
             this.resEventYesService.save(resEventYes, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResEventYes resEventYes) throws StoreException {
        try {
            this.resEventYesService.delete(resEventYes);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


}
