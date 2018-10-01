package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResPlan;
import com.diary.providers.store.res.ResPlanStore;
import com.diary.service.res.ResPlanService;
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
public class ResPlanStoreImpl implements ResPlanStore {

    @Inject
    private ResPlanService resPlanService;


    @Override
    @ConnectManager
    public ResPlan getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resPlanService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public ResPlan getByOrder(Integer displayOrder) throws StoreException {
        try {
            return this.resPlanService.getByOrder(displayOrder);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Integer getMaxOrder() throws StoreException {
        try {
            return this.resPlanService.getMaxOrder();
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<ResPlan> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.resPlanService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResPlan> getList(List<Selector> selectorList) throws StoreException {
        try {
            return this.resPlanService.getList( selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResPlan resPlan) throws StoreException {
        try {
            this.resPlanService.delete(resPlan);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResPlan appUser, Persistent persistent) throws StoreException {
        try {
            this.resPlanService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void saveOrder(ResPlan resPlan, Persistent persistent, ResPlan resPlanOrder) throws StoreException {
        try {
            this.resPlanService.saveOrder(resPlan, persistent,resPlanOrder);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
