package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResHouse;
import com.diary.providers.store.res.ResHouseStore;
import com.diary.service.res.ResHouseService;
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
public class ResHouseStoreImpl implements ResHouseStore {

    @Inject
    private ResHouseService resHouseService;


    @Override
    @ConnectManager
    public ResHouse getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resHouseService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<ResHouse> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.resHouseService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResHouse> getList(List<Selector> selectorList) throws StoreException {
        try {
            return this.resHouseService.getList( selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResHouse resHouse) throws StoreException {
        try {
            this.resHouseService.delete(resHouse);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResHouse appUser, Persistent persistent) throws StoreException {
        try {
            this.resHouseService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
