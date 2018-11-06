package com.diary.storeImpl.app;

import com.diary.common.StoreException;
import com.diary.entity.app.*;
import com.diary.providers.store.app.AppUserFundStore;
import com.diary.service.app.AppUserFundService;
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
public class AppUserFundStoreImpl implements AppUserFundStore {

    @Inject
    private AppUserFundService appUserFundService;


    @Override
    @ConnectManager
    public AppUserFund getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.appUserFundService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<AppUserFund> getByUserId(Long userId) throws StoreException {
        try {
            return this.appUserFundService.getByUserId(userId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Integer getSumByUserId(Long userId) throws StoreException {
        try {
            return this.appUserFundService.getSumByUserId(userId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public AppUserFund getByUserFundId(Long userId,Long fundId) throws StoreException {
        try {
            return this.appUserFundService.getByUserFundId(userId,fundId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }



    @Override
    @ConnectManager
    public void save(AppUserFund appUserFund, Persistent persistent) throws StoreException {
        try {
            this.appUserFundService.save(appUserFund, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(List<AppUserFund> appUserFunds, Persistent persistent) throws StoreException {
        try {
            this.appUserFundService.save(appUserFunds, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(AppUserFund appUserFund, Persistent persistent, AppUserFundDetail appUserFundDetail) throws StoreException {
        try {
            this.appUserFundService.save(appUserFund, persistent,appUserFundDetail);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void saveMan(AppUserFund appUserFund, Persistent persistent, AppUserMan appUserMan,AppUserLimit appUserLimit) throws StoreException {
        try {
            this.appUserFundService.saveMan(appUserFund, persistent,appUserMan,appUserLimit);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void saveLady(AppUserFund appUserFund, Persistent persistent, AppUserLady appUserLady,AppUserLimit appUserLimit) throws StoreException {
        try {
            this.appUserFundService.saveLady(appUserFund, persistent,appUserLady,appUserLimit);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public void deleteMan(AppUserFund appUserFund, AppUserMan appUserMan, AppUserFundMarket appUserFundMarket, List<AppUserFundDetail> appUserFundDetails,
                          AppUserLimit appUserLimit) throws StoreException {
        try {
            this.appUserFundService.deleteMan(appUserFund, appUserMan, appUserFundMarket,appUserFundDetails,appUserLimit);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void deleteLady(AppUserFund appUserFund, AppUserLady appUserLady,AppUserFundMarket appUserFundMarket, List<AppUserFundDetail> appUserFundDetails,
                           AppUserLimit appUserLimit) throws StoreException {
        try {
            this.appUserFundService.deleteLady(appUserFund, appUserLady,appUserFundMarket, appUserFundDetails,appUserLimit);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public void delete(List<AppUserFund> appUserFundList) throws StoreException {
        try {
            this.appUserFundService.delete(appUserFundList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
