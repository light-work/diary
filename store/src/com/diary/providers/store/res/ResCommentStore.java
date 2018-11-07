package com.diary.providers.store.res;


import com.diary.common.StoreException;
import com.diary.entity.res.ResComment;
import org.guiceside.commons.Page;
import org.guiceside.persistence.hibernate.dao.enums.Persistent;
import org.guiceside.persistence.hibernate.dao.hquery.Selector;

import java.util.List;


public interface ResCommentStore {


    ResComment getById(Long id, Selector... selectors) throws StoreException;

    Page<ResComment> getPageList(int start,
                             int limit, List<Selector> selectorList) throws StoreException;

    List<ResComment> getList(Integer gender) throws StoreException;

    void save(ResComment resComment, Persistent persistent) throws StoreException;

    void delete(ResComment resComment) throws StoreException;

}
