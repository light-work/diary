package com.diary.providers.store.app;


import com.diary.common.StoreException;
import com.diary.entity.app.AppUserFundMarket;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;


public interface AppUserFundMarketStore {

    AppUserFundMarket getById(Long id, Selector... selectors) throws StoreException;

    AppUserFundMarket getByUserFundId(Long userId,Long fundId) throws StoreException;

    void save(AppUserFundMarket appUserFundMarket, Persistent persistent) throws StoreException;


    void delete(AppUserFundMarket appUserFundMarket) throws StoreException;

}
