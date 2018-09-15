package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResEventResultEffect;
import com.diary.providers.store.res.ResEventResultEffectStore;
import com.diary.service.res.ResEventResultEffectService;
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
public class ResEventResultEffectStoreImpl implements ResEventResultEffectStore {

    @Inject
    private ResEventResultEffectService resEventResultEffectService;


    @Override
    @ConnectManager
    public ResEventResultEffect getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resEventResultEffectService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResEventResultEffect> getList(List<Selector> selectorList) throws StoreException {
        try {
            return this.resEventResultEffectService.getList(selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResEventResultEffect resEventResultEffect) throws StoreException {
        try {
            this.resEventResultEffectService.delete(resEventResultEffect);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResEventResultEffect appUser, Persistent persistent) throws StoreException {
        try {
            this.resEventResultEffectService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
