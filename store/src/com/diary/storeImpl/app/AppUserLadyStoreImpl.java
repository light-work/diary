package com.diary.storeImpl.app;

import com.diary.common.StoreException;
import com.diary.entity.app.*;
import com.diary.providers.store.app.AppUserLadyStore;
import com.diary.service.app.AppUserLadyService;
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
public class AppUserLadyStoreImpl implements AppUserLadyStore {

    @Inject
    private AppUserLadyService appUserLadyService;


    @Override
    @ConnectManager
    public AppUserLady getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.appUserLadyService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public AppUserLady getByUserId(Long userId) throws StoreException {
        try {
            return this.appUserLadyService.getByUserId(userId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<AppUserLady> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.appUserLadyService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public void save(AppUserLady appUserLady, Persistent persistent) throws StoreException {
        try {
            this.appUserLadyService.save(appUserLady, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(AppUserLady appUserLady, Persistent persistent, AppUser appUser) throws StoreException {
        try {
            this.appUserLadyService.save(appUserLady, persistent,appUser);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(AppUserLady appUserLady, Persistent persistent, AppUserLimit appUserLimit) throws StoreException {
        try {
            this.appUserLadyService.save(appUserLady, persistent,appUserLimit);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void saveDone(AppUserLady appUserLady, Persistent persistent, AppUser appUser, AppUserLadyHist appUserLadyHist, Persistent persistentHist) throws StoreException {
        try {
            this.appUserLadyService.saveDone(appUserLady, persistent, appUser, appUserLadyHist, persistentHist);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void nextDay(AppUserLady appUserLady, Persistent persistent, List<AppUserFund> appUserFunds, List<AppUserFundDetail> appUserFundDetails, List<AppUserFundMarket> appUserFundMarkets) throws StoreException {
        try {
            this.appUserLadyService.nextDay(appUserLady, persistent, appUserFunds, appUserFundDetails, appUserFundMarkets);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(AppUserLady appUserLady, List<AppUserLimit> userLimitList, AppUserJob userJob, List<AppUserClothes> userClothesList, List<AppUserLuxury> userLuxuryList, AppUserCouple userCouple, List<AppUserFund> userFundList, List<AppUserFundMarket> userFundMarketList, List<AppUserFundDetail> userFundDetailList, List<AppUserLuck> userLuckList, List<AppUserPlan> userPlanList) throws StoreException {
        try {
            this.appUserLadyService.delete(appUserLady, userLimitList, userJob, userClothesList, userLuxuryList, userCouple, userFundList, userFundMarketList, userFundDetailList, userLuckList, userPlanList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
