package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResCouple;
import com.diary.providers.store.res.ResCoupleStore;
import com.diary.service.res.ResCoupleService;
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
public class ResCoupleStoreImpl implements ResCoupleStore {

    @Inject
    private ResCoupleService resCoupleService;


    @Override
    @ConnectManager
    public ResCouple getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resCoupleService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public ResCouple getByTitle(String title) throws StoreException {
        try {
            return this.resCoupleService.getByTitle(title);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<ResCouple> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.resCoupleService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public List<ResCouple> getList(List<Selector> selectorList) throws StoreException {
        try {
            return this.resCoupleService.getList( selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResCouple resCouple) throws StoreException {
        try {
            this.resCoupleService.delete(resCouple);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResCouple appUser, Persistent persistent) throws StoreException {
        try {
            this.resCoupleService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
