package com.diary.storeImpl.app;

import com.diary.common.StoreException;
import com.diary.entity.app.*;
import com.diary.providers.store.app.AppUserManStore;
import com.diary.service.app.AppUserManService;
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
public class AppUserManStoreImpl implements AppUserManStore {

    @Inject
    private AppUserManService appUserManService;


    @Override
    @ConnectManager
    public AppUserMan getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.appUserManService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public AppUserMan getByUserId(Long userId) throws StoreException {
        try {
            return this.appUserManService.getByUserId(userId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<AppUserMan> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.appUserManService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public void save(AppUserMan appUserMan, Persistent persistent) throws StoreException {
        try {
            this.appUserManService.save(appUserMan, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void saveDone(AppUserMan appUserMan, Persistent persistent, AppUser appUser, AppUserManHist appUserManHist, Persistent persistentHist) throws StoreException {
        try {
            this.appUserManService.saveDone(appUserMan, persistent,appUser,appUserManHist,persistentHist);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(AppUserMan appUserMan, Persistent persistent, AppUser appUser) throws StoreException {
        try {
            this.appUserManService.save(appUserMan, persistent,appUser);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(AppUserMan appUserMan, Persistent persistent, AppUserLimit appUserLimit) throws StoreException {
        try {
            this.appUserManService.save(appUserMan, persistent,appUserLimit);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void nextDay(AppUserMan appUserMan, Persistent persistent, List<AppUserFund> appUserFunds, List<AppUserFundDetail> appUserFundDetails, List<AppUserFundMarket> appUserFundMarkets) throws StoreException {
        try {
            this.appUserManService.nextDay(appUserMan, persistent, appUserFunds, appUserFundDetails, appUserFundMarkets);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(AppUserMan appUserMan, List<AppUserLimit> userLimitList, AppUserJob userJob, List<AppUserCar> userCarList, List<AppUserHouse> userHouseList, AppUserCouple userCouple, List<AppUserFund> userFundList, List<AppUserFundMarket> userFundMarketList, List<AppUserFundDetail> userFundDetailList, List<AppUserLuck> userLuckList, List<AppUserPlan> userPlanList) throws StoreException {
        try {
            this.appUserManService.delete(appUserMan, userLimitList, userJob, userCarList, userHouseList, userCouple, userFundList, userFundMarketList, userFundDetailList, userLuckList, userPlanList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
