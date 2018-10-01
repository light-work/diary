package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResLuxury;
import com.diary.providers.store.res.ResLuxuryStore;
import com.diary.service.res.ResLuxuryService;
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
public class ResLuxuryStoreImpl implements ResLuxuryStore {

    @Inject
    private ResLuxuryService resLuxuryService;


    @Override
    @ConnectManager
    public ResLuxury getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resLuxuryService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<ResLuxury> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.resLuxuryService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResLuxury> getList(List<Selector> selectorList) throws StoreException {
        try {
            return this.resLuxuryService.getList( selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResLuxury resLuxury) throws StoreException {
        try {
            this.resLuxuryService.delete(resLuxury);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResLuxury appUser, Persistent persistent) throws StoreException {
        try {
            this.resLuxuryService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
