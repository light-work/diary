package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResFund;
import com.diary.providers.store.res.ResFundStore;
import com.diary.service.res.ResFundService;
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
public class ResFundStoreImpl implements ResFundStore {

    @Inject
    private ResFundService resFundService;


    @Override
    @ConnectManager
    public ResFund getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resFundService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<ResFund> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.resFundService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResFund> getList(List<Selector> selectorList) throws StoreException {
        try {
            return this.resFundService.getList( selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResFund resFund) throws StoreException {
        try {
            this.resFundService.delete(resFund);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResFund appUser, Persistent persistent) throws StoreException {
        try {
            this.resFundService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
