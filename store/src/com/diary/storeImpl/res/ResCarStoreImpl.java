package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResCar;
import com.diary.providers.store.res.ResCarStore;
import com.diary.service.res.ResCarService;
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
public class ResCarStoreImpl implements ResCarStore {

    @Inject
    private ResCarService resCarService;


    @Override
    @ConnectManager
    public ResCar getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resCarService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<ResCar> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.resCarService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResCar resCar) throws StoreException {
        try {
            this.resCarService.delete(resCar);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResCar appUser, Persistent persistent) throws StoreException {
        try {
            this.resCarService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
