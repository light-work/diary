package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.*;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserLadyStore {


    AppUserLady getById(Long id, Selector... selectors) throws StoreException;


    Page<AppUserLady> getPageList(int start,
                                 int limit, List<Selector> selectorList) throws StoreException;
    
    AppUserLady getByUserId(Long userId) throws StoreException;

    void save(AppUserLady appUserLady, Persistent persistent) throws StoreException;

    void save(AppUserLady appUserLady, Persistent persistent, AppUser appUser) throws StoreException;

    void nextDay(AppUserLady appUserLady, Persistent persistent, List<AppUserFund> appUserFunds, List<AppUserFundDetail> appUserFundDetails, List<AppUserFundMarket> appUserFundMarkets) throws StoreException;

    void save(AppUserLady appUserLady, Persistent persistent, AppUserLimit appUserLimit) throws StoreException;

    void saveDone(AppUserLady appUserLady, Persistent persistent, AppUser appUser,AppUserLadyHist appUserLadyHist,Persistent persistentHist) throws StoreException;

    void delete(AppUserLady appUserLady,List<AppUserLimit> userLimitList,AppUserJob userJob,
                List<AppUserClothes> userClothesList,List<AppUserLuxury> userLuxuryList,AppUserCouple userCouple,
                List<AppUserFund> userFundList,List<AppUserFundMarket> userFundMarketList, List<AppUserFundDetail> userFundDetailList,
                List<AppUserLuck> userLuckList,List<AppUserPlan> userPlanList) throws StoreException;
}
