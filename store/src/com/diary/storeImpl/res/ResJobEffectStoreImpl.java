package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResJobEffect;
import com.diary.providers.store.res.ResJobEffectStore;
import com.diary.service.res.ResJobEffectService;
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
public class ResJobEffectStoreImpl implements ResJobEffectStore {

    @Inject
    private ResJobEffectService resJobEffectService;


    @Override
    @ConnectManager
    public ResJobEffect getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resJobEffectService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResJobEffect> getList(List<Selector> selectorList) throws StoreException {
        try {
            return this.resJobEffectService.getList(selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResJobEffect> getListByJobId(Long jobId) throws StoreException {
        try {
            return this.resJobEffectService.getListByJobId(jobId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResJobEffect resJobEffect) throws StoreException {
        try {
            this.resJobEffectService.delete(resJobEffect);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResJobEffect appUser, Persistent persistent) throws StoreException {
        try {
            this.resJobEffectService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
