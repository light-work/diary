package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResCoupleEffect;
import com.diary.providers.store.res.ResCoupleEffectStore;
import com.diary.service.res.ResCoupleEffectService;
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
public class ResCoupleEffectStoreImpl implements ResCoupleEffectStore {

    @Inject
    private ResCoupleEffectService resCoupleEffectService;


    @Override
    @ConnectManager
    public ResCoupleEffect getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resCoupleEffectService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResCoupleEffect> getListByCoupleId(Long jobId) throws StoreException {
        try {
            return this.resCoupleEffectService.getListByCoupleId(jobId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResCoupleEffect resCoupleEffect) throws StoreException {
        try {
            this.resCoupleEffectService.delete(resCoupleEffect);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResCoupleEffect appUser, Persistent persistent) throws StoreException {
        try {
            this.resCoupleEffectService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
