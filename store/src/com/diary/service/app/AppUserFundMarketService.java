package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserFundMarket;
import com.diary.providers.store.app.AppUserFundMarketStore;
import com.google.inject.Singleton;
import org.guiceside.persistence.TransactionType;
import org.guiceside.persistence.Transactional;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.HQuery;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

/**
 * Created by Lara Croft on 2016/12/21.
 */
@Singleton
public class AppUserFundMarketService extends HQuery implements AppUserFundMarketStore {


    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserFundMarket getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUserFundMarket.class);
    }


    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserFundMarket getByUserId(Long userId) throws StoreException {
        return $($eq("userId.id", userId)).get(AppUserFundMarket.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(AppUserFundMarket appUserFundMarket) throws StoreException {
        $(appUserFundMarket).delete();
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserFundMarket appUserFundMarket, Persistent persistent) throws StoreException {
        $(appUserFundMarket).save(persistent);

    }

}
