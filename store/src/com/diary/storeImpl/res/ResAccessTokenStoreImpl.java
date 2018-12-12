package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResAccessToken;
import com.diary.providers.store.res.ResAccessTokenStore;
import com.diary.service.res.ResAccessTokenService;
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
public class ResAccessTokenStoreImpl implements ResAccessTokenStore {

    @Inject
    private ResAccessTokenService resAccessTokenService;


    @Override
    @ConnectManager
    public ResAccessToken getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resAccessTokenService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<ResAccessToken> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.resAccessTokenService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResAccessToken> getList(List<Selector> selectorList) throws StoreException {
        try {
            return this.resAccessTokenService.getList( selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResAccessToken resAccessToken) throws StoreException {
        try {
            this.resAccessTokenService.delete(resAccessToken);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResAccessToken appUser, Persistent persistent) throws StoreException {
        try {
            this.resAccessTokenService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
