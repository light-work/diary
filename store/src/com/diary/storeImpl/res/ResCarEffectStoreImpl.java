package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResCarEffect;
import com.diary.providers.store.res.ResCarEffectStore;
import com.diary.service.res.ResCarEffectService;
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
public class ResCarEffectStoreImpl implements ResCarEffectStore {

    @Inject
    private ResCarEffectService resCarEffectService;


    @Override
    @ConnectManager
    public ResCarEffect getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resCarEffectService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResCarEffect> getList(List<Selector> selectorList) throws StoreException {
        try {
            return this.resCarEffectService.getList(selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResCarEffect> getListByCarId(Long jobId) throws StoreException {
        try {
            return this.resCarEffectService.getListByCarId(jobId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResCarEffect resCarEffect) throws StoreException {
        try {
            this.resCarEffectService.delete(resCarEffect);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResCarEffect appUser, Persistent persistent) throws StoreException {
        try {
            this.resCarEffectService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
