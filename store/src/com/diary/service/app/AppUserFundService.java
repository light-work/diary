package com.diary.service.app;

import com.diary.common.StoreException;
import com.diary.entity.app.*;
import com.diary.providers.store.app.AppUserFundStore;
import com.google.inject.Inject;
import com.google.inject.Singleton;
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
public class AppUserFundService extends HQuery implements AppUserFundStore {

    @Inject
    private AppUserFundDetailService appUserFundDetailService;

    @Inject
    private AppUserFundMarketService appUserFundMarketService;

    @Inject
    private AppUserManService appUserManService;

    @Inject
    private AppUserLadyService appUserLadyService;

    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserFund getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(AppUserFund.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<AppUserFund> getByUserId(Long userId) throws StoreException {
        return $($alias("fundId", "fundId"), $eq("userId.id", userId)).list(AppUserFund.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Integer getSumByUserId(Long userId) throws StoreException {
        return $($eq("userId.id", userId), $sum("money")).value(AppUserFund.class, Integer.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Integer getSumBuyByUserId(Long userId) throws StoreException {
        return $($eq("userId.id", userId), $sum("buy")).value(AppUserFund.class, Integer.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public AppUserFund getByUserFundId(Long userId, Long fundId) throws StoreException {
        return $($eq("userId.id", userId), $eq("fundId.id", fundId)).get(AppUserFund.class);
    }


    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void deleteMan(AppUserFund appUserFund, AppUserMan appUserMan, AppUserFundMarket appUserFundMarket, List<AppUserFundDetail> appUserFundDetails,
                          AppUserLimit appUserLimit) throws StoreException {
        $(appUserFund).delete();
        if (appUserFundMarket != null) {
            appUserFundMarketService.delete(appUserFundMarket);
        }
        if (appUserMan != null) {
            appUserManService.save(appUserMan, Persistent.UPDATE, appUserLimit);
        }
        if (appUserFundDetails != null && !appUserFundDetails.isEmpty()) {
            appUserFundDetailService.delete(appUserFundDetails);
        }
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void deleteLady(AppUserFund appUserFund, AppUserLady appUserLady, AppUserFundMarket appUserFundMarket, List<AppUserFundDetail> appUserFundDetails,
                           AppUserLimit appUserLimit) throws StoreException {
        $(appUserFund).delete();
        if (appUserFundMarket != null) {
            appUserFundMarketService.delete(appUserFundMarket);
        }
        if (appUserLady != null) {
            appUserLadyService.save(appUserLady, Persistent.UPDATE, appUserLimit);
        }
        if (appUserFundDetails != null && !appUserFundDetails.isEmpty()) {
            appUserFundDetailService.delete(appUserFundDetails);
        }
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(List<AppUserFund> appUserFundList) throws StoreException {
        $(appUserFundList).delete();
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserFund appUserFund, Persistent persistent) throws StoreException {
        $(appUserFund).save(persistent);

    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(List<AppUserFund> appUserFunds, Persistent persistent) throws StoreException {
        $(appUserFunds).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void saveMan(AppUserFund appUserFund, Persistent persistent, AppUserMan appUserMan, AppUserLimit appUserLimit) throws StoreException {
        $(appUserFund).save(persistent);
        if (appUserMan != null) {
            appUserManService.save(appUserMan, Persistent.UPDATE, appUserLimit);
        }
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void saveLady(AppUserFund appUserFund, Persistent persistent, AppUserLady appUserLady, AppUserLimit appUserLimit) throws StoreException {
        $(appUserFund).save(persistent);
        if (appUserLady != null) {
            appUserLadyService.save(appUserLady, Persistent.UPDATE, appUserLimit);
        }
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(AppUserFund appUserFund, Persistent persistent, AppUserFundDetail appUserFundDetail) throws StoreException {
        $(appUserFund).save(persistent);
        if (appUserFundDetail != null) {
            appUserFundDetailService.save(appUserFundDetail, Persistent.SAVE);
        }
    }

}
