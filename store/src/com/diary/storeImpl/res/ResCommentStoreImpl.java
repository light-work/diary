package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResComment;
import com.diary.providers.store.res.ResCommentStore;
import com.diary.service.res.ResCommentService;
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
public class ResCommentStoreImpl implements ResCommentStore {

    @Inject
    private ResCommentService resCommentService;


    @Override
    @ConnectManager
    public ResComment getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resCommentService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<ResComment> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.resCommentService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResComment> getList(Integer gender) throws StoreException {
        try {
            return this.resCommentService.getList(gender);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResComment resComment) throws StoreException {
        try {
            this.resCommentService.delete(resComment);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResComment appUser, Persistent persistent) throws StoreException {
        try {
            this.resCommentService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
