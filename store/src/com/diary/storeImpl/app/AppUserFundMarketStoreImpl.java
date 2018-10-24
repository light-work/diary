package com.diary.storeImpl.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserFundMarket;
import com.diary.providers.store.app.AppUserFundMarketStore;
import com.diary.service.app.AppUserFundMarketService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;
import org.guiceside.support.hsf.ConnectManager;
import org.hibernate.HibernateException;

/**
 * Created by Lara Croft on 2016/12/21.
 */
@Singleton
public class AppUserFundMarketStoreImpl implements AppUserFundMarketStore {

    @Inject
    private AppUserFundMarketService appUserFundMarketService;


    @Override
    @ConnectManager
    public AppUserFundMarket getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.appUserFundMarketService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public AppUserFundMarket getByUserId(Long userId) throws StoreException {
        try {
            return this.appUserFundMarketService.getByUserId(userId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }



    @Override
    @ConnectManager
    public void save(AppUserFundMarket appUserFundMarket, Persistent persistent) throws StoreException {
        try {
            this.appUserFundMarketService.save(appUserFundMarket, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }



    @Override
    @ConnectManager
    public void delete(AppUserFundMarket appUserFundMarket) throws StoreException {
        try {
            this.appUserFundMarketService.delete(appUserFundMarket);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
