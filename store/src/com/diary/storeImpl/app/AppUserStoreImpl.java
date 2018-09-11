package com.diary.storeImpl.app;

import com.diary.common.StoreException;
import com.diary.entity.app.AppUser;
import com.diary.providers.store.app.AppUserStore;
import com.diary.service.app.AppUserService;
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
public class AppUserStoreImpl implements AppUserStore {

    @Inject
    private AppUserService appUserService;


    @Override
    @ConnectManager
    public AppUser getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.appUserService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<AppUser> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.appUserService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public AppUser getByOpenId(String openId) throws StoreException {
        try {
            return this.appUserService.getByOpenId(openId);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(AppUser appUser, Persistent persistent) throws StoreException {
        try {
            this.appUserService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
