package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.*;
import com.diary.providers.store.app.AppUserLadyStore;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.guiceside.commons.Page;
import org.guiceside.persistence.TransactionType;
import org.guiceside.persistence.Transactional;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.HQuery;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;

/**
 * Created by Lara Croft on 2016/12/21.
 */
@Singleton
public class AppUserLadyService extends HQuery implements AppUserLadyStore {

    @Inject
    private AppUserLimitService appUserLimitService;

    @Inject
    private AppUserFundService appUserFundService;

    @Inject
    private AppUserFundDetailService appUserFundDetailService;

    @Inject
    private AppUserFundMarketService appUserFundMarketService;

    @Inject
    private AppUserService appUserService;

    @Inject
    private AppUserLadyHistService appUserLadyHistService;


    @Inject
    private AppUserJobService appUserJobService;

    @Inject
    private AppUserClothesService appUserClothesService;

    @Inject
    private AppUserLuxuryService appUserLuxuryService;

    @Inject
    private AppUserCoupleService appUserCoupleService;

    @Inject
    private AppUserLuckService appUserLuckService;

    @Inject
    private AppUserPlanService appUserPlanService;

    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserLady getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUserLady.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<AppUserLady> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(AppUserLady.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserLady getByUserId(Long userId) throws StoreException {
        return $($alias("userId", "userId"), $eq("userId.id", userId)).get(AppUserLady.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserLady appUserLady, Persistent persistent) throws StoreException {
        $(appUserLady).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserLady appUserLady, Persistent persistent, AppUser appUser) throws StoreException {
        $(appUserLady).save(persistent);
        appUserService.save(appUser, Persistent.UPDATE);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void nextDay(AppUserLady appUserLady, Persistent persistent, List<AppUserFund> appUserFunds, List<AppUserFundDetail> appUserFundDetails, List<AppUserFundMarket> appUserFundMarkets) throws StoreException {
        $(appUserLady).save(persistent);
        if (appUserFunds != null && !appUserFunds.isEmpty()) {
            appUserFundService.save(appUserFunds, Persistent.UPDATE);
        }
        if (appUserFundDetails != null && !appUserFundDetails.isEmpty()) {
            appUserFundDetailService.save(appUserFundDetails, Persistent.SAVE);
        }
        if (appUserFundMarkets != null && !appUserFundMarkets.isEmpty()) {
            appUserFundMarketService.save(appUserFundMarkets, Persistent.UPDATE);
        }
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void saveDone(AppUserLady appUserLady, Persistent persistent, AppUser appUser, AppUserLadyHist appUserLadyHist, Persistent persistentHist) throws StoreException {
        $(appUserLady).save(persistent);
        appUserService.save(appUser, Persistent.UPDATE);
        appUserLadyHistService.save(appUserLadyHist, persistentHist);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserLady appUserLady, Persistent persistent, AppUserLimit appUserLimit) throws StoreException {
        $(appUserLady).save(persistent);
        if (appUserLimit != null) {
            appUserLimitService.save(appUserLimit, Persistent.SAVE);
        }
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(AppUserLady appUserLady, List<AppUserLimit> userLimitList, AppUserJob userJob, List<AppUserClothes> userClothesList, List<AppUserLuxury> userLuxuryList, AppUserCouple userCouple, List<AppUserFund> userFundList, List<AppUserFundMarket> userFundMarketList, List<AppUserFundDetail> userFundDetailList, List<AppUserLuck> userLuckList, List<AppUserPlan> userPlanList) throws StoreException {
        $(appUserLady).save(Persistent.UPDATE);
        if (userLimitList != null && !userLimitList.isEmpty()) {
            appUserLimitService.delete(userLimitList);
        }
        if (userJob != null) {
            appUserJobService.delete(userJob);
        }
        if (userClothesList != null && !userClothesList.isEmpty()) {
            appUserClothesService.delete(userClothesList);
        }
        if (userLuxuryList != null && !userLuxuryList.isEmpty()) {
            appUserLuxuryService.delete(userLuxuryList);
        }
        if (userCouple != null) {
            appUserCoupleService.delete(userCouple);
        }
        if (userFundList != null && !userFundList.isEmpty()) {
            appUserFundService.delete(userFundList);
        }
        if (userFundMarketList != null && !userFundMarketList.isEmpty()) {
            appUserFundMarketService.delete(userFundMarketList);
        }
        if (userFundDetailList != null && !userFundDetailList.isEmpty()) {
            appUserFundDetailService.delete(userFundDetailList);
        }
        if (userLuckList != null && !userLuckList.isEmpty()) {
            appUserLuckService.delete(userLuckList);
        }
        if (userPlanList != null && !userPlanList.isEmpty()) {
            appUserPlanService.delete(userPlanList);
        }
    }
}
