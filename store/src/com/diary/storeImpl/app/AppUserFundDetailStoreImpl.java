package com.diary.storeImpl.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUserFundDetail;
import com.diary.providers.store.app.AppUserFundDetailStore;
import com.diary.service.app.AppUserFundDetailService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;
import org.guiceside.support.hsf.ConnectManager;
import org.hibernate.HibernateException;

import java.util.List;

/**
 * Created by Lara Croft on 2016/12/21.
 */
@Singleton
public class AppUserFundDetailStoreImpl implements AppUserFundDetailStore {

    @Inject
    private AppUserFundDetailService appUserFundDetailService;


    @Override
    @ConnectManager
    public AppUserFundDetail getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.appUserFundDetailService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }


    @Override
    @ConnectManager
    public List<AppUserFundDetail> getByUserFundId(Long userFundId) throws StoreException {
        try {
            return this.appUserFundDetailService.getByUserFundId(userFundId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(AppUserFundDetail appUserFundDetail, Persistent persistent) throws StoreException {
        try {
            this.appUserFundDetailService.save(appUserFundDetail, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(List<AppUserFundDetail> appUserFundDetails, Persistent persistent) throws StoreException {
        try {
            this.appUserFundDetailService.save(appUserFundDetails, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(AppUserFundDetail appUserFundDetail) throws StoreException {
        try {
            this.appUserFundDetailService.delete(appUserFundDetail);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(List<AppUserFundDetail> appUserFundDetailList) throws StoreException {
        try {
            this.appUserFundDetailService.delete(appUserFundDetailList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
