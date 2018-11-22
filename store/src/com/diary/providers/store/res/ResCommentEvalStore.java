package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResCommentEval;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResCommentEvalStore {


    ResCommentEval getById(Long id, Selector... selectors) throws StoreException;

    Page<ResCommentEval> getPageList(int start,
                                 int limit, List<Selector> selectorList) throws StoreException;

    List<ResCommentEval> getList(Integer gender,String evalKey) throws StoreException;

    void save(ResCommentEval resCommentEval, Persistent persistent) throws StoreException;

    void delete(ResCommentEval resCommentEval) throws StoreException;

}
