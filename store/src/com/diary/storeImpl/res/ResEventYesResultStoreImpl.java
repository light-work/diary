package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEventYes;
import com.diary.entity.res.ResEventYesResult;
import com.diary.providers.store.res.ResEventYesResultStore;
import com.diary.service.res.ResEventYesResultService;
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
public class ResEventYesResultStoreImpl implements ResEventYesResultStore {

    @Inject
    private ResEventYesResultService resEventYesResultService;


    @Override
    @ConnectManager
    public ResEventYesResult getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resEventYesResultService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResEventYesResult> getByYesId(Long yesId) throws StoreException {
        try {
            return this.resEventYesResultService.getByYesId(yesId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResEventYesResult> getByList(List<Selector> selectorList) throws StoreException {
        try {
            return this.resEventYesResultService.getByList(selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResEventYesResult resEventYesResult, Persistent persistent) throws StoreException {
        try {
             this.resEventYesResultService.save(resEventYesResult, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResEventYesResult resEventYesResult) throws StoreException {
        try {
            this.resEventYesResultService.delete(resEventYesResult);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


}
