package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.*;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserManStore {


    AppUserMan getById(Long id, Selector... selectors) throws StoreException;


    Page<AppUserMan> getPageList(int start,
                                 int limit, List<Selector> selectorList) throws StoreException;

    AppUserMan getByUserId(Long userId) throws StoreException;

    void save(AppUserMan appUserMan, Persistent persistent) throws StoreException;

    void nextDay(AppUserMan appUserMan, Persistent persistent, List<AppUserFund> appUserFunds, List<AppUserFundDetail> appUserFundDetails, List<AppUserFundMarket> appUserFundMarkets) throws StoreException;

    void save(AppUserMan appUserMan, Persistent persistent, AppUserLimit appUserLimit) throws StoreException;

    void save(AppUserMan appUserMan, Persistent persistent, AppUser appUser) throws StoreException;

    void saveDone(AppUserMan appUserMan, Persistent persistent, AppUser appUser,AppUserManHist appUserManHist,Persistent persistentHist) throws StoreException;

    void delete(AppUserMan appUserMan,List<AppUserLimit> userLimitList,AppUserJob userJob,
                List<AppUserCar> userCarList,List<AppUserHouse> userHouseList,AppUserCouple userCouple,
                List<AppUserFund> userFundList,List<AppUserFundMarket> userFundMarketList, List<AppUserFundDetail> userFundDetailList,
                List<AppUserLuck> userLuckList,List<AppUserPlan> userPlanList) throws StoreException;
}
