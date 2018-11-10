package com.diary.service.res;

import com.diary.common.StoreException;
import com.diary.entity.res.ResCommentEval;
import com.diary.providers.store.res.ResCommentEvalStore;
import com.google.inject.Singleton;
import org.guiceside.commons.Page;
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
public class ResCommentEvalService extends HQuery implements ResCommentEvalStore {

    @Override
    @Transactional(type = TransactionType.READ_ONLY)

    public ResCommentEval getById(Long id, Selector... selectors) throws StoreException {
        return $(id, selectors).get(ResCommentEval.class);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public Page<ResCommentEval> getPageList(int start, int limit, List<Selector> selectorList) throws StoreException {
        return $(selectorList).page(ResCommentEval.class, start, limit);
    }

    @Override
    @Transactional(type = TransactionType.READ_ONLY)
    public List<ResCommentEval> getList(Integer gender,String evalKey) throws StoreException {
        return $($eq("gender",gender),$eq("evalKey",evalKey)).list(ResCommentEval.class);
    }



    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void save(ResCommentEval resCommentEval, Persistent persistent) throws StoreException {
        $(resCommentEval).save(persistent);
    }

    @Override
    @Transactional(type = TransactionType.READ_WRITE)
    public void delete(ResCommentEval resCommentEval) throws StoreException {
        $(resCommentEval).delete();
    }
}
