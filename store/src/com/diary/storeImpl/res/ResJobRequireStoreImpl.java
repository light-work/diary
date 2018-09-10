package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResJobRequire;
import com.diary.providers.store.res.ResJobRequireStore;
import com.diary.service.res.ResJobRequireService;
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
public class ResJobRequireStoreImpl implements ResJobRequireStore {

    @Inject
    private ResJobRequireService resJobRequireService;


    @Override
    @ConnectManager
    public ResJobRequire getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resJobRequireService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResJobRequire> getList(List<Selector> selectorList) throws StoreException {
        try {
            return this.resJobRequireService.getList(selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResJobRequire> getListByJobId(Long jobId) throws StoreException {
        try {
            return this.resJobRequireService.getListByJobId(jobId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResJobRequire resJobRequire) throws StoreException {
        try {
            this.resJobRequireService.delete(resJobRequire);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResJobRequire appUser, Persistent persistent) throws StoreException {
        try {
            this.resJobRequireService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
