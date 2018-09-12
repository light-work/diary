package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResHouseEffect;
import com.diary.providers.store.res.ResHouseEffectStore;
import com.diary.service.res.ResHouseEffectService;
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
public class ResHouseEffectStoreImpl implements ResHouseEffectStore {

    @Inject
    private ResHouseEffectService resHouseEffectService;


    @Override
    @ConnectManager
    public ResHouseEffect getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resHouseEffectService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResHouseEffect> getListByHouseId(Long jobId) throws StoreException {
        try {
            return this.resHouseEffectService.getListByHouseId(jobId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResHouseEffect> getList(List<Selector> selectorList) throws StoreException {
        try {
            return this.resHouseEffectService.getList(selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResHouseEffect resHouseEffect) throws StoreException {
        try {
            this.resHouseEffectService.delete(resHouseEffect);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public void save(ResHouseEffect appUser, Persistent persistent) throws StoreException {
        try {
            this.resHouseEffectService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
