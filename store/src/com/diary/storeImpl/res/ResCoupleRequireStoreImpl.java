package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResCoupleRequire;
import com.diary.providers.store.res.ResCoupleRequireStore;
import com.diary.service.res.ResCoupleRequireService;
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
public class ResCoupleRequireStoreImpl implements ResCoupleRequireStore {

    @Inject
    private ResCoupleRequireService resCoupleRequireService;


    @Override
    @ConnectManager
    public ResCoupleRequire getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resCoupleRequireService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResCoupleRequire> getListByCoupleId(Long jobId) throws StoreException {
        try {
            return this.resCoupleRequireService.getListByCoupleId(jobId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResCoupleRequire resCoupleRequire) throws StoreException {
        try {
            this.resCoupleRequireService.delete(resCoupleRequire);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResCoupleRequire appUser, Persistent persistent) throws StoreException {
        try {
            this.resCoupleRequireService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
