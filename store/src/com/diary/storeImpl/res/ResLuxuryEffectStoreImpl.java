package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResLuxuryEffect;
import com.diary.providers.store.res.ResLuxuryEffectStore;
import com.diary.service.res.ResLuxuryEffectService;
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
public class ResLuxuryEffectStoreImpl implements ResLuxuryEffectStore {

    @Inject
    private ResLuxuryEffectService resLuxuryEffectService;


    @Override
    @ConnectManager
    public ResLuxuryEffect getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resLuxuryEffectService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResLuxuryEffect> getList(List<Selector> selectorList) throws StoreException {
        try {
            return this.resLuxuryEffectService.getList(selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResLuxuryEffect> getListByLuxuryId(Long jobId) throws StoreException {
        try {
            return this.resLuxuryEffectService.getListByLuxuryId(jobId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResLuxuryEffect resLuxuryEffect) throws StoreException {
        try {
            this.resLuxuryEffectService.delete(resLuxuryEffect);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResLuxuryEffect appUser, Persistent persistent) throws StoreException {
        try {
            this.resLuxuryEffectService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
