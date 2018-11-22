package com.diary.storeImpl.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserHouse;
import com.diary.entity.app.AppUserLimit;
import com.diary.entity.app.AppUserMan;
import com.diary.providers.store.app.AppUserHouseStore;
import com.diary.service.app.AppUserHouseService;
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
public class AppUserHouseStoreImpl implements AppUserHouseStore {

    @Inject
    private AppUserHouseService appUserHouseService;


    @Override
    @ConnectManager
    public AppUserHouse getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.appUserHouseService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public List<AppUserHouse> getByUserId(Long userId) throws StoreException {
        try {
            return this.appUserHouseService.getByUserId(userId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Integer getMaxLevelByUserId(Long userId) throws StoreException {
        try {
            return this.appUserHouseService.getMaxLevelByUserId(userId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Integer getCountByUserId(Long userId) throws StoreException {
        try {
            return this.appUserHouseService.getCountByUserId(userId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<AppUserHouse> getByUserIdHouseId(Long userId, Long houseId) throws StoreException {
        try {
            return this.appUserHouseService.getByUserIdHouseId(userId,houseId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<AppUserHouse> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.appUserHouseService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public void save(AppUserHouse appUserHouse, Persistent persistent) throws StoreException {
        try {
            this.appUserHouseService.save(appUserHouse, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public void delete(AppUserHouse appUserHouse) throws StoreException {
        try {
            this.appUserHouseService.delete(appUserHouse);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(List<AppUserHouse> appUserHouseList) throws StoreException {
        try {
            this.appUserHouseService.delete(appUserHouseList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void buy(AppUserHouse appUserHouse, Persistent persistent, AppUserMan appUserMan,AppUserLimit appUserLimit) throws StoreException {
        try {
            this.appUserHouseService.buy(appUserHouse,persistent,appUserMan,appUserLimit);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void sell(AppUserHouse appUserHouse, AppUserMan appUserMan,AppUserLimit appUserLimit) throws StoreException {
        try {
            this.appUserHouseService.sell(appUserHouse,appUserMan,appUserLimit);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
