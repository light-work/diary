package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResClothesEffect;
import com.diary.providers.store.res.ResClothesEffectStore;
import com.diary.service.res.ResClothesEffectService;
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
public class ResClothesEffectStoreImpl implements ResClothesEffectStore {

    @Inject
    private ResClothesEffectService resClothesEffectService;


    @Override
    @ConnectManager
    public ResClothesEffect getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resClothesEffectService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResClothesEffect> getList(List<Selector> selectorList) throws StoreException {
        try {
            return this.resClothesEffectService.getList(selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResClothesEffect> getListByClothesId(Long jobId) throws StoreException {
        try {
            return this.resClothesEffectService.getListByClothesId(jobId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResClothesEffect resClothesEffect) throws StoreException {
        try {
            this.resClothesEffectService.delete(resClothesEffect);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResClothesEffect appUser, Persistent persistent) throws StoreException {
        try {
            this.resClothesEffectService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
