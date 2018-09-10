package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResPlanEffect;
import com.diary.providers.store.res.ResPlanEffectStore;
import com.diary.service.res.ResPlanEffectService;
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
public class ResPlanEffectStoreImpl implements ResPlanEffectStore {

    @Inject
    private ResPlanEffectService resPlanEffectService;


    @Override
    @ConnectManager
    public ResPlanEffect getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resPlanEffectService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResPlanEffect> getListByPlanId(Long jobId) throws StoreException {
        try {
            return this.resPlanEffectService.getListByPlanId(jobId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResPlanEffect resPlanEffect) throws StoreException {
        try {
            this.resPlanEffectService.delete(resPlanEffect);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResPlanEffect appUser, Persistent persistent) throws StoreException {
        try {
            this.resPlanEffectService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
