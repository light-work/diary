package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResLuck;
import com.diary.providers.store.res.ResLuckStore;
import com.diary.service.res.ResLuckService;
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
public class ResLuckStoreImpl implements ResLuckStore {

    @Inject
    private ResLuckService resLuckService;


    @Override
    @ConnectManager
    public ResLuck getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resLuckService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<ResLuck> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.resLuckService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResLuck> getList(List<Selector> selectorList) throws StoreException {
        try {
            return this.resLuckService.getList( selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResLuck resLuck) throws StoreException {
        try {
            this.resLuckService.delete(resLuck);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResLuck appUser, Persistent persistent) throws StoreException {
        try {
            this.resLuckService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
