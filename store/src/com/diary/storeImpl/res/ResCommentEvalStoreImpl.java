package com.diary.storeImpl.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResCommentEval;
import com.diary.providers.store.res.ResCommentEvalStore;
import com.diary.service.res.ResCommentEvalService;
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
public class ResCommentEvalStoreImpl implements ResCommentEvalStore {

    @Inject
    private ResCommentEvalService resCommentEvalService;


    @Override
    @ConnectManager
    public ResCommentEval getById(Long id, Selector... selectors) throws StoreException {
        try {
            return this.resCommentEvalService.getById(id, selectors);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public Page<ResCommentEval> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        try {
            return this.resCommentEvalService.getPageList(start, limit, selectorList);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public List<ResCommentEval> getList(Integer gender,String evalKey) throws StoreException {
        try {
            return this.resCommentEvalService.getList(gender,evalKey);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void delete(ResCommentEval resCommentEval) throws StoreException {
        try {
            this.resCommentEvalService.delete(resCommentEval);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }

    @Override
    @ConnectManager
    public void save(ResCommentEval appUser, Persistent persistent) throws StoreException {
        try {
            this.resCommentEvalService.save(appUser, persistent);
        } catch (HibernateException e) {
            Throwable throwable = e.getCause() != null ? e.getCause() : e;
            throw new StoreException(throwable.getLocalizedMessage(), e.fillInStackTrace());
        }
    }
}
