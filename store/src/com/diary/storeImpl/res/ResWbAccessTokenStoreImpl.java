package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResWbAccessToken;
import com.diary.providers.store.res.ResWbAccessTokenStore;
import com.diary.service.res.ResWbAccessTokenService;
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
public class ResWbAccessTokenStoreImpl implements ResWbAccessTokenStore {

    @Inject
    private ResWbAccessTokenService resWbAccessTokenService;


    @Override
    @ConnectManager
    public ResWbAccessToken getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resWbAccessTokenService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<ResWbAccessToken> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.resWbAccessTokenService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResWbAccessToken> getList(List<Selector> selectorList) throws StoreException {
        try {
            return this.resWbAccessTokenService.getList( selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResWbAccessToken resWbAccessToken) throws StoreException {
        try {
            this.resWbAccessTokenService.delete(resWbAccessToken);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResWbAccessToken appUser, Persistent persistent) throws StoreException {
        try {
            this.resWbAccessTokenService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
