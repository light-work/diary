package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResClothes;
import com.diary.providers.store.res.ResClothesStore;
import com.diary.service.res.ResClothesService;
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
public class ResClothesStoreImpl implements ResClothesStore {

    @Inject
    private ResClothesService resClothesService;


    @Override
    @ConnectManager
    public ResClothes getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resClothesService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public ResClothes getByTitle(String title) throws StoreException {
        try {
            return this.resClothesService.getByTitle(title);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<ResClothes> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.resClothesService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResClothes> getList(List<Selector> selectorList) throws StoreException {
        try {
            return this.resClothesService.getList( selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResClothes resClothes) throws StoreException {
        try {
            this.resClothesService.delete(resClothes);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResClothes appUser, Persistent persistent) throws StoreException {
        try {
            this.resClothesService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
