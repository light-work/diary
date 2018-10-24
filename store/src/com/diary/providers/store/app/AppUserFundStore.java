package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.*;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface AppUserFundStore {

    AppUserFund getById(Long id, Selector... selectors) throws StoreException;

    AppUserFund getByUserFundId(Long userId,Long fundId) throws StoreException;

    Integer getSumByUserId(Long userId) throws StoreException;

    void save(AppUserFund appUserFund, Persistent persistent) throws StoreException;

    void save(AppUserFund appUserFund, Persistent persistent,  AppUserFundDetail appUserFundDetail) throws StoreException;

    void saveMan(AppUserFund appUserFund, Persistent persistent, AppUserMan appUserMan) throws StoreException;

    void saveLady(AppUserFund appUserFund, Persistent persistent, AppUserLady appUserLady) throws StoreException;

    void deleteMan(AppUserFund appUserFund, AppUserMan appUserMan, AppUserFundMarket appUserFundMarket, List<AppUserFundDetail> appUserFundDetails) throws StoreException;

    void deleteLady(AppUserFund appUserFund, AppUserLady appUserLady,AppUserFundMarket appUserFundMarket, List<AppUserFundDetail> appUserFundDetails) throws StoreException;

}
